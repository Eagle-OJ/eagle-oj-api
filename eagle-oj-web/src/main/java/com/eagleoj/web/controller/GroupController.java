package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.format.user.PullUsersIntoContestFormat;
import com.eagleoj.web.controller.format.user.SendGroupUserMessageFormat;
import com.eagleoj.web.postman.task.SendGroupUserMessageTask;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.controller.exception.UnauthorizedException;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.user.*;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.postman.MessageQueue;
import com.eagleoj.web.postman.task.PullGroupUserIntoContestTask;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.ContestService;
import com.eagleoj.web.service.GroupService;
import com.eagleoj.web.service.GroupUserService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private MessageQueue messageQueue;

    @ApiOperation("获取小组的信息")
    @GetMapping("/{gid}")
    public ResponseEntity getGroup(@PathVariable("gid") int gid,
                                   @RequestParam(name = "isDetail", defaultValue = "false", required = false) boolean isDetail) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);
        UserEntity userEntity = userService.getUserByUid(groupEntity.getOwner());
        String json = JSON.toJSONString(groupEntity);
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.put("nickname", userEntity.getNickname());

        if (isDetail && SecurityUtils.getSubject().isAuthenticated()) {
            int uid = SessionHelper.get().getUid();
            if (uid != userEntity.getUid()) {
                throw new UnauthorizedException();
            }
        } else {
            // 对非本人创建小组屏蔽password
            if (jsonObject.containsKey("password")) {
                jsonObject.replace("password", "You can't see it!");
            }
        }
        return new ResponseEntity(jsonObject);
    }

    @ApiOperation("创建小组")
    @RequiresAuthentication
    @PostMapping
    public ResponseEntity createGroup(@RequestBody @Valid CreateGroupFormat format) {
        int owner = SessionHelper.get().getUid();
        int gid = groupService.createGroup(owner, format.getName(), format.getPassword(), System.currentTimeMillis());

        if (gid == 0) {
            throw new WebErrorException("小组创建失败");
        }
        return new ResponseEntity("小组创建成功", gid);
    }

    @ApiOperation("更新小组信息")
    @RequiresAuthentication
    @PutMapping("/{gid}")
    public ResponseEntity updateGroup(@PathVariable int gid,
                                      @RequestBody @Valid CreateGroupFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);
        havePermission(groupEntity);

        if (! groupService.updateGroup(gid, format.getName(), format.getPassword())) {
            throw new WebErrorException("小组更新失败");
        }
        return new ResponseEntity("小组更新成功");
    }

    @ApiOperation("获取小组里面的所有组员")
    @GetMapping("/{gid}/members")
    public ResponseEntity getGroupMembers(@PathVariable("gid") int gid,
                                          @RequestParam("page") int page,
                                          @RequestParam("page_size") int pageSize,
                                          @RequestParam(name = "isDetail", required = false, defaultValue = "false") boolean isDetail) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);

        PageRowBounds pager = new PageRowBounds(page, pageSize);
        List<Map<String, Object>> members = groupUserService.getMembers(gid, pager);
        if (isDetail && SecurityUtils.getSubject().isAuthenticated()) {
            int uid = SessionHelper.get().getUid();
            if (uid != groupEntity.getOwner()) {
                throw new UnauthorizedException();
            }
        } else {
            // 隐藏关键信息
            for (Map<String, Object> member: members) {
                member.remove("real_name");
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("data", members);
        map.put("total", pager.getTotal());
        return new ResponseEntity(map);
    }

    @ApiOperation("加入小组")
    @RequiresAuthentication
    @PostMapping("/{gid}/enter")
    public ResponseEntity enterGroup(@PathVariable("gid") int gid,
                                     @RequestBody @Valid EnterGroupFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);

        // 密码校对
        if (groupEntity.getPassword() != null) {
            if (! format.getPassword().equals(groupEntity.getPassword())) {
                throw new WebErrorException("密码错误");
            }
        }

        // 查看是否已经在小组里面
        int uid = SessionHelper.get().getUid();
        if (groupUserService.isIn(gid, uid)) {
            throw new WebErrorException("已经在小组里面了");
        }

        // 加入小组
        if (! groupUserService.add(gid, uid)) {
            throw new WebErrorException("加入小组失败");
        }

        return new ResponseEntity("小组加入成功");
    }

    @ApiOperation("获取用户在小组中的信息")
    @RequiresAuthentication
    @GetMapping("/{gid}/user/{uid}")
    public ResponseEntity getMeInfo(@PathVariable("gid") int gid,
                                    @PathVariable("uid") int uid) {
        if (uid != SessionHelper.get().getUid()) {
            throw new UnauthorizedException();
        }

        GroupUserEntity entity = groupUserService.getMember(gid, uid);
        if (entity == null) {
            throw new WebErrorException("用户不在小组中");
        }

        if (entity.getRealName() == null) {
            entity.setRealName("");
        }
        return new ResponseEntity(entity);
    }

    @ApiOperation("踢出用户或者自己退出")
    @RequiresAuthentication
    @DeleteMapping("/{gid}/user/{uid}")
    public ResponseEntity kickUser(@PathVariable int gid,
                                   @PathVariable int uid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);

        //  校验被踢出或者自己退出
        if (! (SessionHelper.get().getUid() == uid)) {
            havePermission(groupEntity);
        }

        // 删除用户
        if (! groupUserService.deleteMember(gid, uid)) {
            throw new WebErrorException("删除用户失败");
        }
        return new ResponseEntity("用户删除成功");
    }

    @ApiOperation("更新用户组内名称")
    @RequiresAuthentication
    @PutMapping("/{gid}/user/{uid}")
    public ResponseEntity updateUserRealName(@PathVariable int gid,
                                             @PathVariable int uid,
                                             @RequestBody @Valid UpdateGroupUserFormat format) {
        if (uid != SessionHelper.get().getUid()) {
            throw new UnauthorizedException();
        }

        if (! groupUserService.updateRealName(gid, uid, format.getRealName())) {
            throw new WebErrorException("更新失败");
        }

        return new ResponseEntity("更新成功");
    }

    @ApiOperation("将用户拉入某个比赛")
    @RequiresAuthentication
    @PostMapping("/{gid}/pull_contest")
    public ResponseEntity pullUsersIntoContest(@PathVariable int gid,
                                               @RequestBody @Valid PullUsersIntoContestFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);
        havePermission(groupEntity);

        ContestEntity contestEntity = contestService.getContestByCid(format.getCid());
        haveContest(contestEntity);

        String password = contestEntity.getPassword();
        if (password!= null) {
            if (! password.equals(format.getPassword()) ) {
                throw new WebErrorException("小组密码错误");
            }
        }

        PullGroupUserIntoContestTask task = new PullGroupUserIntoContestTask(gid, groupEntity.getName(),
                format.getCid(), contestEntity.getName());
        task.setType(3);
        messageQueue.addTask(task);
        return new ResponseEntity("操作成功");
    }

    @ApiOperation("给组内成员发送通知")
    @RequiresAuthentication
    @PostMapping("/{gid}/message")
    public ResponseEntity sendGroupUserMessage(@PathVariable int gid,
                                               @RequestBody @Valid SendGroupUserMessageFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);
        havePermission(groupEntity);

        SendGroupUserMessageTask task = new SendGroupUserMessageTask(groupEntity.getGid(), groupEntity.getName(),
                format.getMessage());
        task.setType(4);
        messageQueue.addTask(task);
        return new ResponseEntity("通知发送成功");
    }

    private void haveGroup(GroupEntity entity) {
        WebUtil.assertNotNull(entity, "小组不存在");
    }

    private void haveContest(ContestEntity entity) {
        WebUtil.assertNotNull(entity, "比赛不存在");
    }

    private void havePermission(GroupEntity groupEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (! (uid == groupEntity.getOwner() || role == DefaultConfig.ADMIN_ROLE)) {
            throw new WebErrorException("只允许本人操作");
        }
    }
}
