package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.controller.format.user.SendGroupUserMessageFormat;
import com.eagleoj.web.data.status.ContestStatus;
import com.eagleoj.web.data.status.RoleStatus;
import com.eagleoj.web.postman.task.SendGroupUserMessageTask;
import com.eagleoj.web.service.ContestService;
import com.eagleoj.web.service.async.AsyncTaskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.controller.exception.UnauthorizedException;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.user.*;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.postman.TaskQueue;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.GroupService;
import com.eagleoj.web.service.GroupUserService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private AsyncTaskService asyncTaskService;

    @Autowired
    private ContestService contestService;

    @ApiOperation("获取小组的公共信息")
    @GetMapping("/{gid}/info")
    public ResponseEntity getGroupInfo(@PathVariable("gid") int gid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        UserEntity userEntity = userService.getUserByUid(groupEntity.getOwner());
        String json = JSON.toJSONString(groupEntity);
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.put("nickname", userEntity.getNickname());
        if (jsonObject.containsKey("password")) {
            jsonObject.replace("password", "You can't see it!");
        }
        return new ResponseEntity(jsonObject);
    }

    @ApiOperation("获取小组信息")
    @RequiresAuthentication
    @GetMapping("/{gid}")
    public ResponseEntity getGroup(@PathVariable("gid") int gid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToEditGroup(groupEntity);
        return new ResponseEntity(groupEntity);
    }

    @ApiOperation("获取用户在小组中的信息")
    @RequiresAuthentication
    @GetMapping("/{gid}/user/{uid}")
    public ResponseEntity getMeInfo(@PathVariable("gid") int gid,
                                    @PathVariable("uid") int uid) {
        try {
            return new ResponseEntity(groupUserService.getGroupUserInfo(gid, uid));
        } catch (Exception e) {
            return new ResponseEntity(null);
        }
    }

    @ApiOperation("创建小组")
    @RequiresAuthentication
    @PostMapping
    public ResponseEntity createGroup(@RequestBody @Valid CreateGroupFormat format) {
        int owner = SessionHelper.get().getUid();
        int gid = groupService.saveGroup(owner, format.getName(), format.getPassword());
        return new ResponseEntity("小组创建成功", gid);
    }

    @ApiOperation("删除小组")
    @RequiresAuthentication
    @DeleteMapping("/{gid}")
    public ResponseEntity deleteGroup(@PathVariable int gid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToEditGroup(groupEntity);
        groupService.deleteGroup(gid);
        // todo 删除小组发送消息
        return new ResponseEntity("小组解散成功");
    }

    @ApiOperation("更新小组信息")
    @RequiresAuthentication
    @PutMapping("/{gid}")
    public ResponseEntity updateGroup(@PathVariable int gid,
                                      @RequestBody @Valid CreateGroupFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToEditGroup(groupEntity);

        if (! groupService.updateGroupByGid(gid, format.getName(), format.getPassword())) {
            throw new WebErrorException("小组更新失败");
        }
        return new ResponseEntity("小组更新成功");
    }

    @ApiOperation("获取小组里面的所有组员")
    @RequiresAuthentication
    @GetMapping("/{gid}/members")
    public ResponseEntity getGroupMembers(@PathVariable("gid") int gid,
                                          @RequestParam("page") int page,
                                          @RequestParam("page_size") int pageSize,
                                          @RequestParam(name = "rank", required = false, defaultValue = "False") boolean withRank) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToViewGroup(groupEntity);
        Page pager = PageHelper.startPage(page, pageSize);
        if (withRank) {
            return new ResponseEntity(WebUtil.generatePageData(pager, groupUserService.listGroupMembersRank(gid)));
        } else {
            return new ResponseEntity(WebUtil.generatePageData(pager, groupUserService.listGroupMembers(gid)));
        }
    }

    @ApiOperation("获取小组的小组赛")
    @RequiresAuthentication
    @GetMapping("/{gid}/contests")
    public ResponseEntity getGroupContests(@PathVariable int gid,
                                           @RequestParam("page") int page,
                                           @RequestParam("page_size") int pageSize,
                                           @RequestParam("is_valid") boolean isValid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToViewGroup(groupEntity);
        Page pager = PageHelper.startPage(page, pageSize);
        ContestStatus status;
        if (isValid) {
            status = ContestStatus.USING;
        } else {
            status = null;
        }
        return new ResponseEntity(WebUtil.generatePageData(pager, contestService.listGroupContests(gid, status)));
    }

    @ApiOperation("加入小组")
    @RequiresAuthentication
    @PostMapping("/{gid}/enter")
    public ResponseEntity enterGroup(@PathVariable("gid") int gid,
                                     @RequestBody @Valid EnterGroupFormat format) {
        groupUserService.joinGroup(gid, SessionHelper.get().getUid(), format.getPassword());
        return new ResponseEntity("小组加入成功");
    }

    @ApiOperation("更新用户组内名称")
    @RequiresAuthentication
    @PutMapping("/{gid}/user/{uid}")
    public ResponseEntity updateUserGroupName(@PathVariable int gid,
                                             @PathVariable int uid,
                                             @RequestBody @Valid UpdateGroupUserFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToEditOwnInfo(uid, groupEntity);
        groupUserService.updateGroupName(gid, uid, format.getGroupName());
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("踢出用户或者自己退出")
    @RequiresAuthentication
    @DeleteMapping("/{gid}/user/{uid}")
    public ResponseEntity kickUser(@PathVariable int gid,
                                   @PathVariable int uid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToEditOwnInfo(uid, groupEntity);

        // 删除用户
        groupUserService.deleteGroupMember(gid, uid);
        return new ResponseEntity("组内用户删除成功");
    }

   /* @ApiOperation("将用户拉入某个比赛")
    @RequiresAuthentication
    @PostMapping("/{gid}/pull_contest")
    public ResponseEntity pullUsersIntoContest(@PathVariable int gid,
                                               @RequestBody @Valid PullUsersIntoContestFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToEditGroup(groupEntity);

        ContestEntity contestEntity = contestService.getContest(format.getCid());

        String password = contestEntity.getPassword();
        if (password!= null) {
            if (! password.equals(format.getPassword()) ) {
                throw new WebErrorException("小组密码错误");
            }
        }

        PullGroupUserIntoContestTask task = new PullGroupUserIntoContestTask(gid, groupEntity.getName(),
                format.getCid(), contestEntity.getName(), contestEntity.getPassword());
        task.setType(3);
        messageQueue.addTask(task);
        return new ResponseEntity("操作成功");
    }*/

    @ApiOperation("给组内成员发送通知")
    @RequiresAuthentication
    @PostMapping("/{gid}/message")
    public ResponseEntity sendGroupUserMessage(@PathVariable int gid,
                                               @RequestBody @Valid SendGroupUserMessageFormat format) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        accessToEditGroup(groupEntity);
        asyncTaskService.sendGroupMessage(format.getMessage(), groupEntity.getName(), gid);
        return new ResponseEntity("通知发送成功");
    }

    private void accessToEditGroup(GroupEntity groupEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();

        if (uid == groupEntity.getOwner()) {
            return;
        }

        if (role >= RoleStatus.ADMIN.getNumber()) {
            return;
        }

        throw new UnauthorizedException();
    }

    private void accessToEditOwnInfo(int uid, GroupEntity groupEntity) {
        if (uid == SessionHelper.get().getUid()) {
            return;
        }

        accessToEditGroup(groupEntity);

        throw new UnauthorizedException();
    }

    private void accessToViewGroup( GroupEntity groupEntity) {
        int uid = SessionHelper.get().getUid();
        try {
            accessToEditGroup(groupEntity);
        } catch (Exception e) {
            WebUtil.assertIsSuccess(groupUserService.isUserInGroup(groupEntity.getGid(), uid), "你无权查看本小组信息");
        }
    }
}
