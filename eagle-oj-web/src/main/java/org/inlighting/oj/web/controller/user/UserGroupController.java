package org.inlighting.oj.web.controller.user;

import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.format.user.CreateGroupFormat;
import org.inlighting.oj.web.controller.format.user.EnterGroupFormat;
import org.inlighting.oj.web.entity.GroupEntity;
import org.inlighting.oj.web.entity.GroupUserInfoEntity;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.GroupService;
import org.inlighting.oj.web.service.GroupUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserGroupController {

    private GroupService groupService;

    private GroupUserInfoService groupUserInfoService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setGroupUserInfoService(GroupUserInfoService groupUserInfoService) {
        this.groupUserInfoService = groupUserInfoService;
    }

    @ApiOperation("创建小组")
    @PostMapping
    public ResponseEntity createGroup(@RequestBody @Valid CreateGroupFormat format) {
        // todo
        int owner = SessionHelper.get().getUid();
        int gid = groupService.createGroup(owner, format.getCover(), format.getName(), format.getPassword(), System.currentTimeMillis());

        if (gid == 0) {
            throw new RuntimeException("比赛创建失败");
        }
        return new ResponseEntity("比赛创建成功", gid);
    }

    @ApiOperation("加入小组")
    @PostMapping("/{gid}/enter")
    public ResponseEntity enterGroup(@PathVariable("gid") int gid,
                                     @RequestBody @Valid EnterGroupFormat format) {
        // todo
        // 校对密码
        GroupEntity groupEntity = groupService.getByGid(gid);
        if (groupEntity == null) {
            throw new RuntimeException("次小组不存在");
        }

        // 查看是否已经在小组里面
        int uid = SessionHelper.get().getUid();
        GroupUserInfoEntity groupUserInfoEntity = groupUserInfoService.getByGidAndUid(gid, uid);
        if (groupUserInfoEntity != null) {
            throw new RuntimeException("已经在小组里面了");
        }

        // 密码校对
        if (groupEntity.getPassword() != null) {
            if (! format.getPassword().equals(groupEntity.getPassword())) {
                throw new RuntimeException("密码错误");
            }
        }

        // 加入小组
        if (! groupUserInfoService.add(gid, uid, System.currentTimeMillis())) {
            throw new RuntimeException("加入小组失败");
        }

        return new ResponseEntity("小组加入成功");
    }

    @ApiOperation("踢出用户")
    @PostMapping("/{gid}/user/{uid}/kick")
    public ResponseEntity kickUser(@PathVariable int gid,
                                   @PathVariable int uid) {
        // todo
        int owner = SessionHelper.get().getUid();

        // 检验自己是否为小组长
        GroupEntity groupEntity = groupService.getByGid(gid);
        if (groupEntity.getOwner() != owner) {
            throw new RuntimeException("你没有管理权限");
        }

        // 删除用户
        if (! groupUserInfoService.deleteByGidAndUid(gid, uid)) {
            throw new RuntimeException("删除用户失败");
        }
        return new ResponseEntity("用户删除成功");
    }
}
