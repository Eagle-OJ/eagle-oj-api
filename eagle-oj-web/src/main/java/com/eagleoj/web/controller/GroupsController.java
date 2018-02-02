package com.eagleoj.web.controller;

import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

    @ApiOperation("获取用户创建的小组")
    @GetMapping("/user")
    public ResponseEntity getUserGroups(@RequestParam("page") int page,
                                    @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int uid = SessionHelper.get().getUid();
        List<GroupEntity> groups = groupService.listUserGroups(uid);
        return new ResponseEntity(WebUtil.generatePageData(pager, groups));
    }


    @ApiOperation("获取所有小组")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @GetMapping
    public ResponseEntity getGroups(@RequestParam("page") int page,
                                    @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, groupService.listAll()));
    }
}
