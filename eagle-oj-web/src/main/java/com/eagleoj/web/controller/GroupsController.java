package com.eagleoj.web.controller;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import com.eagleoj.web.controller.exception.UnauthorizedException;
import com.eagleoj.web.entity.GroupEntity;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupsController {

    @Autowired
    private GroupService groupService;

    @ApiOperation("获取小组列表")
    @GetMapping
    public ResponseEntity getGroups(@RequestParam("page") int page,
                                    @RequestParam("page_size") int pageSize,
                                    @RequestParam(name = "owner", required = false, defaultValue = "0") int owner) {
        if (owner > 0) {
            // 鉴权
            Subject subject = SecurityUtils.getSubject();
            if (! subject.isAuthenticated()) {
                throw new UnauthorizedException();
            }
            int uid = SessionHelper.get().getUid();
            if (uid != owner) {
                throw new UnauthorizedException();
            }

            PageRowBounds pager = new PageRowBounds(page, pageSize);
            List<GroupEntity> groups = groupService.getGroups(uid, pager);
            Map<String, Object> data = new HashMap<>(2);
            data.put("data", groups);
            data.put("total", pager.getTotal());
            return new ResponseEntity(data);
        } else {
            return new ResponseEntity(new ArrayList<>());
        }
    }
}
