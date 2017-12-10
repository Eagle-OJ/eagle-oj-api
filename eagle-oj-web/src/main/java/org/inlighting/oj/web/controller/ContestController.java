package org.inlighting.oj.web.controller;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.entity.ContestEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.service.ContestService;
import org.inlighting.oj.web.service.ContestUserService;
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
        List<HashMap<String, Object>> contests = contestService.getValidContests(pager);
        for (HashMap<String, Object> contest: contests) {
            if (contest.get("password") != null) {
                contest.replace("password", "You can't see it!");
            }
        }
        data.put("data", contests);
        data.put("total", pager.getTotal());
        return new ResponseEntity(data);
    }

    @ApiOperation("获取某个比赛信息")
    @GetMapping("/{cid}")
    public ResponseEntity getContest(@PathVariable("cid") int cid) {
        ContestEntity contestEntity = contestService.getContestByCid(cid);
        if (contestEntity.getPassword()!=null) {
            contestEntity.setPassword("You can't see it");
        }
        return new ResponseEntity(contestEntity);
    }
}
