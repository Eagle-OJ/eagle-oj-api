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

    @Autowired
    private ContestService contestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContestProblemService contestProblemService;

    @Autowired
    private ProblemService problemService;

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
        haveProblem(problemEntity);

        // 加载本次比赛中此题的提交情况
        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(pid, cid);
        WebUtil.assertNotNull(contestProblemEntity, "本次比赛不包含此题");
        problemEntity.setSubmitTimes(contestProblemEntity.getSubmitTimes());
        problemEntity.setACTimes(contestProblemEntity.getACTimes());
        problemEntity.setCETimes(contestProblemEntity.getCETimes());
        problemEntity.setWATimes(contestProblemEntity.getWATimes());
        problemEntity.setTLETimes(contestProblemEntity.getTLETimes());
        problemEntity.setRTETimes(contestProblemEntity.getRTETimes());

        // 加载用户信息
        UserEntity userEntity = userService.getUserByUid(problemEntity.getOwner());

        // 加载比赛信息
        ContestEntity contestEntity = contestService.getContestByCid(cid);
        boolean contestStatus = false;
        if (contestEntity.getStatus() == 1) {
            contestStatus = true;
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("problem", problemEntity);
        Map<String, Object> userMap = new HashMap<>(2);
        userMap.put("nickname", userEntity.getNickname());
        userMap.put("avatar", userEntity.getAvatar());
        map.put("author", userMap);
        Map<String, Object> contestMap = new HashMap<>(2);
        contestMap.put("status", contestStatus);
        contestMap.put("name", contestEntity.getName());
        map.put("contest", contestMap);
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

    private void haveProblem(ProblemEntity problemEntity) {
        WebUtil.assertNotNull(problemEntity, "此题不存在");
    }
}
