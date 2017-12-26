package org.inlighting.oj.web.controller;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.entity.*;
import org.inlighting.oj.web.service.*;
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
@RequestMapping(value = "/contest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContestController {

    private ContestService contestService;

    private ContestUserService contestUserInfoService;

    private UserService userService;

    private ContestProblemService contestProblemService;

    private ProblemService problemService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setContestProblemService(ContestProblemService contestProblemService) {
        this.contestProblemService = contestProblemService;
    }

    @Autowired
    public void setContestUserInfoService(ContestUserService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @ApiOperation("获取比赛列表")
    @GetMapping
    public ResponseEntity getContests(@RequestParam("page") int page,
                                      @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> contests = contestService.getValidContests(pager);
        for (Map<String, Object> contest: contests) {
            if (contest.get("password") != null) {
                contest.replace("password", "You can't see it!");
            }
        }
        data.put("data", contests);
        data.put("total", pager.getTotal());
        return new ResponseEntity(data);
    }

    @ApiOperation("获取比赛中题目的信息")
    @GetMapping("/{cid}/problem/{pid}")
    public ResponseEntity getContestProblemInfo(@PathVariable("cid") int cid,
                                                @PathVariable("pid") int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new WebErrorException("此题不存在");
        }
        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(pid, cid);
        problemEntity.setSubmitTimes(contestProblemEntity.getSubmitTimes());
        problemEntity.setACTimes(contestProblemEntity.getACTimes());
        problemEntity.setCETimes(contestProblemEntity.getCETimes());
        problemEntity.setWATimes(contestProblemEntity.getWATimes());
        problemEntity.setTLETimes(contestProblemEntity.getTLETimes());
        problemEntity.setRTETimes(contestProblemEntity.getRTETimes());
        UserEntity userEntity = userService.getUserByUid(problemEntity.getOwner());
        Map<String, Object> map = new HashMap<>(2);
        map.put("problem", problemEntity);
        map.put("author", userEntity.getNickname());
        return new ResponseEntity(map);
    }

    @ApiOperation("获取某个比赛信息")
    @GetMapping("/{cid}")
    public ResponseEntity getContest(@PathVariable("cid") int cid) {
        ContestEntity contestEntity = contestService.getContestByCid(cid);
        WebUtil.assertNotNull(contestEntity, "不存在次比赛");
        if (contestEntity.getPassword()!=null) {
            contestEntity.setPassword("You can't see it");
        }
        return new ResponseEntity(contestEntity);
    }

}
