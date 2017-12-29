package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.entity.GroupEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.UserEntity;
import org.inlighting.oj.web.service.GroupService;
import org.inlighting.oj.web.service.GroupUserService;
import org.inlighting.oj.web.service.UserService;
import org.inlighting.oj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private UserService userService;

    private GroupService groupService;

    private GroupUserService groupUserService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setGroupUserService(GroupUserService groupUserService) {
        this.groupUserService = groupUserService;
    }

    @GetMapping("/{gid}")
    public ResponseEntity getGroup(@PathVariable("gid") int gid) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        haveGroup(groupEntity);
        UserEntity userEntity = userService.getUserByUid(groupEntity.getOwner());
        String json = JSON.toJSONString(groupEntity);
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.put("nickname", userEntity.getNickname());
        if (jsonObject.containsKey("password")) {
            jsonObject.replace("password", "You can't see it!");
        }
        return new ResponseEntity(jsonObject);
    }

    @ApiOperation("获取小组里面的所有组员")
    @GetMapping("/{gid}/members")
    public ResponseEntity getGroupMembers(@PathVariable("gid") int gid,
                                          @RequestParam("page") int page,
                                          @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        Map<String, Object> map = new HashMap<>(2);
        List<Map<String, Object>> members = groupUserService.getMembers(gid, pager);
        for (Map<String, Object> member: members) {
            member.remove("real_name");
        }
        map.put("data", members);
        map.put("total", pager.getTotal());
        return new ResponseEntity(map);
    }

    private void haveGroup(GroupEntity entity) {
        WebUtil.assertNotNull(entity, "小组不存在");
    }
}
