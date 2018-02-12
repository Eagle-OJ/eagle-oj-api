package com.eagleoj.web.controller;

import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.data.status.RoleStatus;
import com.eagleoj.web.entity.ContestEntity;
import com.eagleoj.web.entity.ProblemEntity;
import com.eagleoj.web.service.*;
import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.security.SessionHelper;
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

    @Autowired
    private ProblemUserService problemUserService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ContestProblemUserService contestProblemUserService;

    @Autowired
    private ContestService contestService;

    @ApiOperation("获取用户的代码记录")
    @RequiresAuthentication
    @GetMapping
    public ResponseEntity getUserSubmissions(@RequestParam("pid") int pid,
                                             @RequestParam("cid") Integer cid,
                                             @RequestParam("page") int page,
                                             @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int owner = SessionHelper.get().getUid();
        if (cid == 0) {
            cid = null;
        }
        return new ResponseEntity(WebUtil.generatePageData(pager,
                submissionService.listOwnSubmissions(owner, pid, cid)));
    }

    @ApiOperation("获取本题的所有代码")
    @RequiresAuthentication
    @GetMapping("/all")
    public ResponseEntity getProblemSubmissions(@RequestParam("pid") int pid,
                                                @RequestParam("cid") int cid,
                                                @RequestParam("page") int page,
                                                @RequestParam("page_size") int pageSize) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        ProblemEntity problemEntity = problemService.getProblem(pid);

        if (cid != 0) {
            ContestEntity contestEntity = contestService.getContest(cid);
            // 比赛管理者可以查看当前比赛此题的提交
            if (contestEntity.getOwner() == uid) {
                return returnAllSubmissions(pid, cid, page, pageSize);
            }

            // admin
            if (role == RoleStatus.ADMIN.getNumber() || role == RoleStatus.ROOT.getNumber()) {
                return returnAllSubmissions(pid, cid, page, pageSize);
            }

            // AC
            try {
                if (contestProblemUserService.getByCidPidUid(cid, pid, uid).getStatus() == ResultEnum.AC) {
                    return returnAllSubmissions(pid, cid, page, pageSize);
                }
            } catch (Exception e) { }
        } else {
            // 出题者可以直接看
            if (problemEntity.getOwner() == uid) {
                return returnAllSubmissions(pid, 0, page, pageSize);
            }

            // admin
            if (role == RoleStatus.ADMIN.getNumber() || role == RoleStatus.ROOT.getNumber()) {
                return returnAllSubmissions(pid, 0, page, pageSize);
            }

            // AC
            try {
                if (problemUserService.get(pid, uid).getStatus() == ResultEnum.AC) {
                    return returnAllSubmissions(pid, 0, page, pageSize);
                }
            } catch (Exception e) {}
        }

        return new ResponseEntity("你暂时没有资格查看", null);
    }

    private ResponseEntity returnAllSubmissions(int pid, int cid, int page, int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, submissionService.listProblemSubmissions(pid, cid)));
    }
}
