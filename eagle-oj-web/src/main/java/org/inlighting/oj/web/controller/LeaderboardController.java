package org.inlighting.oj.web.controller;

import io.swagger.annotations.ApiOperation;
import org.ehcache.Cache;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.entity.ContestEntity;
import org.inlighting.oj.web.entity.ContestProblemUserEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.service.ContestProblemUserService;
import org.inlighting.oj.web.service.ContestService;
import org.inlighting.oj.web.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/leaderboard", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LeaderboardController {

    private LeaderboardService leaderboardService;

    private ContestService contestService;

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @Autowired
    public void setLeaderboardService(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    public ResponseEntity getGlobalRank() {
        return null;
    }

    @ApiOperation("获取某个比赛的排行榜")
    @GetMapping("/contest/{cid}")
    @SuppressWarnings("unchecked")
    public ResponseEntity getContestLeaderboard(@PathVariable int cid) {
        List<Map<String, Object>> list = leaderboardService.getContestLeaderboard(cid);
        if (list == null) {
            throw new WebErrorException("不存在此比赛");
        }
        return new ResponseEntity(list);
    }
}
