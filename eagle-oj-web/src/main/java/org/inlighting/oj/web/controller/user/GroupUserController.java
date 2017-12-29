package org.inlighting.oj.web.controller.user;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.DefaultConfig;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.user.CreateGroupFormat;
import org.inlighting.oj.web.controller.format.user.EnterGroupFormat;
import org.inlighting.oj.web.entity.GroupEntity;
import org.inlighting.oj.web.entity.GroupUserEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.GroupService;
import org.inlighting.oj.web.service.GroupUserService;
import org.inlighting.oj.web.util.WebUtil;
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
@RequestMapping(value = "/user/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupUserController {

    private GroupService groupService;

    private GroupUserService groupUserService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setGroupUserInfoService(GroupUserService groupUserService) {
        this.groupUserService = groupUserService;
    }

    @ApiOperation("获取我管理的小组")
    @GetMapping
    public ResponseEntity getGroups(@RequestParam("page") int page,
                                    @RequestParam("page_size") int pageSize) {
        int uid = SessionHelper.get().getUid();
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        List<GroupEntity> groups = groupService.getGroups(uid, pager);
        Map<String, Object> data = new HashMap<>(2);
        data.put("data", groups);
        data.put("total", pager.getTotal());
        return new ResponseEntity(data);
    }

    @ApiOperation("获取小组信息")
    @GetMapping("/{gid}")
    public ResponseEntity getGroup(@PathVariable("gid") int gid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);
        havePermission(groupEntity);
        return new ResponseEntity(groupEntity);
    }

    @ApiOperation("创建小组")
    @PostMapping
    public ResponseEntity createGroup(@RequestBody @Valid CreateGroupFormat format) {
        int owner = SessionHelper.get().getUid();
        int gid = groupService.createGroup(owner, format.getName(), format.getPassword(), System.currentTimeMillis());

        if (gid == 0) {
            throw new WebErrorException("小组创建失败");
        }
        return new ResponseEntity("小组创建成功", gid);
    }

    @ApiOperation("更新小组")
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

    @ApiOperation("判断用户是否在小组中")
    @GetMapping("/{gid}/is_in")
    public ResponseEntity getMeInfo(@PathVariable("gid") int gid) {
        GroupUserEntity entity = groupUserService.getMember(gid, SessionHelper.get().getUid());
        boolean isIn = entity !=null;
        return new ResponseEntity(isIn);
    }

    @ApiOperation("加入小组")
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
        GroupUserEntity groupUserEntity = groupUserService.getMember(gid, uid);
        if (groupUserEntity != null) {
            throw new WebErrorException("已经在小组里面了");
        }

        // 加入小组
        if (! groupUserService.add(gid, uid)) {
            throw new WebErrorException("加入小组失败");
        }

        return new ResponseEntity("小组加入成功");
    }

    @ApiOperation("踢出用户或者自己退出")
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

    private void haveGroup(GroupEntity entity) {
        WebUtil.assertNotNull(entity, "不存在此小组");
    }

    private void havePermission(GroupEntity groupEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (! (uid == groupEntity.getOwner() || role == DefaultConfig.ADMIN_ROLE)) {
            throw new WebErrorException("只允许本人操作");
        }
    }
}
