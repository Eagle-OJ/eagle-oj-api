package org.inlighting.oj.web.controller;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.service.ProblemUserService;
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
@RequestMapping(value = "/problem_user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProblemUserController {

    private ProblemUserService problemUserService;

    @Autowired
    public void setProblemUserService(ProblemUserService problemUserService) {
        this.problemUserService = problemUserService;
    }

    @ApiOperation("获取最近10条用户做题记录")
    @GetMapping
    public ResponseEntity getProblemUser(@RequestParam("uid") int uid,
                                         @RequestParam("page") int page,
                                         @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        List<Map<String, Object>> list = problemUserService.getProblemUser(uid, pager);
        Map<String, Object> data = new HashMap<>(2);
        data.put("data", list);
        data.put("total", pager.getTotal());
        return new ResponseEntity(data);
    }
}
