package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.service.ProblemService;
import org.inlighting.oj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/problem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProblemController {

    private ProblemService problemService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @ApiOperation("获取公开题目列表")
    @GetMapping
    public ResponseEntity getProblems(@RequestParam("page") int page,
                                      @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        return new ResponseEntity(problemService.getSharedProblems(pager));
    }


    @GetMapping("/{pid}")
    public ResponseEntity get(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new WebErrorException("此题不存在");
        }
        return new ResponseEntity(problemEntity);
    }

    @ApiOperation("获取该题目的problem的所有moderator")
    @GetMapping("/{pid}/moderators")
    public ResponseEntity getProblemModerators(@PathVariable("pid") int pid) {
        // todo
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);

        JSONArray moderators = problemEntity.getModerators();
        if(moderators.size() == 0) {
            return new ResponseEntity(new JSONArray());
        }

        List<Integer> uidList = new ArrayList<>(moderators.size());
        for (int i=0; i<moderators.size(); i++) {
            uidList.add(moderators.getInteger(i));
        }

        return new ResponseEntity(userService.getModeratorsInUidList(uidList));
    }

    @ApiOperation("获取该题的所有标签")
    @GetMapping("/{pid}/tags")
    public ResponseEntity getProblemTags(@PathVariable("pid") int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        return new ResponseEntity(problemService.getProblemTags(pid));
    }

    private void haveProblem(ProblemEntity entity) {
        if (entity == null) {
            throw new WebErrorException("题目不存在");
        }
    }
}
