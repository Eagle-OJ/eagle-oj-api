package org.inlighting.oj.web.controller.user;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.DefaultConfig;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.user.CreateGroupFormat;
import org.inlighting.oj.web.controller.format.user.EnterGroupFormat;
import org.inlighting.oj.web.entity.GroupEntity;
import org.inlighting.oj.web.entity.GroupUserInfoEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.GroupService;
import org.inlighting.oj.web.service.GroupUserInfoService;
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

    @ApiOperation("获取我的小组")
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

    @ApiOperation("加入小组")
    @PostMapping("/{gid}/enter")
    public ResponseEntity enterGroup(@PathVariable("gid") int gid,
                                     @RequestBody @Valid EnterGroupFormat format) {
        // 校对密码
        GroupEntity groupEntity = groupService.getGroup(gid);
        if (groupEntity == null) {
            throw new WebErrorException("次小组不存在");
        }

        // 查看是否已经在小组里面
        int uid = SessionHelper.get().getUid();
        GroupUserInfoEntity groupUserInfoEntity = groupUserInfoService.getByGidAndUid(gid, uid);
        if (groupUserInfoEntity != null) {
            throw new WebErrorException("已经在小组里面了");
        }

        // 密码校对
        if (groupEntity.getPassword() != null) {
            if (! format.getPassword().equals(groupEntity.getPassword())) {
                throw new WebErrorException("密码错误");
            }
        }

        // 加入小组
        if (! groupUserInfoService.add(gid, uid, System.currentTimeMillis())) {
            throw new WebErrorException("加入小组失败");
        }

        return new ResponseEntity("小组加入成功");
    }

    @ApiOperation("踢出用户")
    @PostMapping("/{gid}/user/{uid}/kick")
    public ResponseEntity kickUser(@PathVariable int gid,
                                   @PathVariable int uid) {
        int owner = SessionHelper.get().getUid();

        // 检验自己是否为小组长
        GroupEntity groupEntity = groupService.getGroup(gid);
        if (groupEntity.getOwner() != owner) {
            throw new WebErrorException("你没有管理权限");
        }

        // 删除用户
        if (! groupUserInfoService.deleteByGidAndUid(gid, uid)) {
            throw new WebErrorException("删除用户失败");
        }
        return new ResponseEntity("用户删除成功");
    }

    private void haveGroup(GroupEntity entity) {
        if (entity == null) {
            throw new WebErrorException("不存在此比赛");
        }
    }

    private void havePermission(GroupEntity groupEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (! (uid == groupEntity.getOwner() || role == DefaultConfig.ADMIN_ROLE)) {
            throw new WebErrorException("只允许本人操作");
        }
    }
}
