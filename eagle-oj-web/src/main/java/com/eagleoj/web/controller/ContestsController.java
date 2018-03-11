package com.eagleoj.web.controller;

import com.eagleoj.web.entity.ContestEntity;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.ContestService;
import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/contests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContestsController {

    @Autowired
    private ContestService contestService;

    @ApiOperation("获取开启的比赛列表-不包含小组赛")
    @GetMapping("/opened")
    public ResponseEntity listOpenedContests(@RequestParam("page") int page,
                                      @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> contests = contestService.listOpenedContests();
        for (Map<String, Object> contest: contests) {
            if (contest.get("password") != null) {
                contest.replace("password", "You can't see it!");
            }
        }
        return new ResponseEntity(WebUtil.generatePageData(pager, contests));
    }

    @ApiOperation("获取用户创建的比赛")
    @RequiresAuthentication
    @GetMapping("/user")
    public ResponseEntity listUserContests(@RequestParam("page") int page,
                                           @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int uid = SessionHelper.get().getUid();
        List<ContestEntity> list = contestService.listUserContests(uid, 0);
        return new ResponseEntity(WebUtil.generatePageData(pager, list));
    }

    @ApiOperation("获取所有比赛列表")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @GetMapping("/all")
    public ResponseEntity listAllContests(@RequestParam("page") int page,
                                          @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> contests = contestService.listAllContests();
        return new ResponseEntity(WebUtil.generatePageData(pager, contests));
    }
}
