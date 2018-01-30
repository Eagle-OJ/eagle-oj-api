package com.eagleoj.web.controller;

import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/submissions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SubmissionsController {

    @Autowired
    private SubmissionService submissionService;

    @ApiOperation("获取用户的代码记录")
    @RequiresAuthentication
    @GetMapping
    public ResponseEntity getUserSubmissions(@RequestParam("cid") int cid,
                                             @RequestParam("pid") int pid,
                                             @RequestParam("gid") int gid,
                                             @RequestParam("page") int page,
                                             @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int owner = SessionHelper.get().getUid();
        return new ResponseEntity(WebUtil.generatePageData(pager,
                submissionService.listSubmissions(owner, pid, cid, gid)));
    }
}
