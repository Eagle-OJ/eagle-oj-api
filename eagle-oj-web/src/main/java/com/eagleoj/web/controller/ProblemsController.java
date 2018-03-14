package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.ImportProblemsFormat;
import com.eagleoj.web.data.status.RoleStatus;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.ProblemService;
import com.eagleoj.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/problems", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProblemsController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProblemService problemService;

    @ApiOperation("获取用户创建的题目")
    @RequiresAuthentication
    @GetMapping("/user")
    public ResponseEntity getUserProblems(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "page_size") int pageSize) {
        int uid = SessionHelper.get().getUid();
        Page pager = PageHelper.startPage(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, problemService.listUserProblems(uid)));
    }

    @ApiOperation("获取公开题目列表-否是附带用户提交状态")
    @GetMapping("/shared")
    public ResponseEntity getProblems(@RequestParam(name = "tag", defaultValue = "null", required = false) String tag,
                                      @RequestParam(name = "difficult", defaultValue = "-1", required = false) Integer difficult,
                                      @RequestParam(name = "query", defaultValue = "null", required = false) String query,
                                      @RequestParam(name = "sort", defaultValue = "null", required = false) String sort,
                                      @RequestParam("page") int page,
                                      @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> data;

        if (tag.equals("null")) {
            tag = null;
        }

        if (difficult == -1) {
            difficult = null;
        }

        if (query.equals("null")) {
            query = null;
        }

        Integer uid = null;
        if (SecurityUtils.getSubject().isAuthenticated()) {
            uid = SessionHelper.get().getUid();
        }

        Integer iSort = null;
        switch (sort) {
            case "pid.desc":
                iSort = 0;
                break;
            case "submit_times.desc":
                iSort = 1;
                break;
            case "submit_times.asc":
                iSort = 2;
                break;
            case "ac_rate.desc":
                iSort = 3;
                break;
            case "ac_rate.asc":
                iSort = 4;
                break;
            default:
                iSort = null;
        }

        data = problemService.listSharedProblems(tag, difficult, uid, query, iSort);
        return new ResponseEntity(WebUtil.generatePageData(pager, data));
    }

    @ApiOperation("获取比赛添加题目列表")
    @RequiresAuthentication
    @GetMapping("/contest")
    public ResponseEntity listContestProblemsForAdding(@RequestParam("page") int page,
                                                       @RequestParam("page_size") int pageSize,
                                                       @RequestParam(name = "query", required = false, defaultValue = "null") String query) {
        int uid = SessionHelper.get().getUid();
        Page pager = PageHelper.startPage(page, pageSize);
        query = query.equals("null")? null: query;
        return new ResponseEntity(WebUtil.generatePageData(pager, problemService.listProblemsForContest(uid, query)));
    }

    @ApiOperation("获取所有题目")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @GetMapping
    public ResponseEntity getAllProblems(@RequestParam("page") int page,
                                              @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, problemService.listAllProblems()));
    }

    @ApiOperation("获取待审核的题目列表")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @GetMapping("/auditing")
    public ResponseEntity getAuditingProblems(@RequestParam("page") int page,
                                              @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, problemService.listAuditingProblems()));
    }

    @ApiOperation("导出题目")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PostMapping("/export")
    public ResponseEntity exportProblems(@RequestBody @Valid ImportProblemsFormat format) {
        JSONArray pidList = format.getPidList();
        if (pidList.size() == 0) {
            throw new WebErrorException("列表不得为空");
        }
        if (! problemService.exportProblems(pidList)) {
            throw new WebErrorException("题目导出失败");
        }
        return new ResponseEntity("题目导出成功");
    }

    @ApiOperation("导入题目")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PostMapping("/import")
    public ResponseEntity importProblems(@RequestParam("file") MultipartFile uploadFile) {
        try {
            InputStream is = uploadFile.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String temp = reader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (temp != null) {
                stringBuilder.append(temp);
                temp = reader.readLine();
            }
            int uid = SessionHelper.get().getUid();
            if (! problemService.importProblems(uid, JSON.parseArray(stringBuilder.toString()))) {
                throw new WebErrorException("题目导入失败");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw  new WebErrorException("题目上传失败");
        }
        return new ResponseEntity("题目导入成功");
    }
}
