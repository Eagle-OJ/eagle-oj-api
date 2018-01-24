package org.inlighting.oj.web.controller;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.ProblemService;
import org.inlighting.oj.web.util.WebUtil;
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
@RequestMapping(value = "/problems", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProblemsController {

    @Autowired
    private ProblemService problemService;

    @ApiOperation("获取用户创建的题目")
    @RequiresAuthentication
    @GetMapping("/user")
    public ResponseEntity getUserProblems(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "page_size") int pageSize) {
        int uid = SessionHelper.get().getUid();
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, problemService.getProblemsByUid(uid, pager)));
    }

    @ApiOperation("获取公开题目列表")
    @GetMapping
    public ResponseEntity getProblems(@RequestParam(name = "difficult", defaultValue = "-1", required = false) int difficult,
                                      @RequestParam(name = "tag", defaultValue = "null", required = false) String tag,
                                      @RequestParam(name = "uid", defaultValue = "-1", required = false) int uid,
                                      @RequestParam("page") int page,
                                      @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        if (tag.equals("null")) {
            tag = null;
        }
        Map<String, Object> result = new HashMap<>(2);
        List<Map<String, Object>> data;
        if (uid != -1) {
            // 返回带status结果的数据
            data = problemService.getSharedProblemsWithStatus(uid, difficult, tag, pager);
        } else {
            // 返回不带status结果的数据
            data = problemService.getSharedProblems(difficult, tag, pager);
        }
        result.put("problems", data);
        result.put("total", pager.getTotal());
        return new ResponseEntity(result);
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @GetMapping("/auditing")
    public ResponseEntity getAuditingProblems(@RequestParam("page") int page,
                                              @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, problemService.getAuditingProblems(pager)));
    }
}
