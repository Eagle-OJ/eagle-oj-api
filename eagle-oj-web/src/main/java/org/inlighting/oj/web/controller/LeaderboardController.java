package org.inlighting.oj.web.controller;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.Api;
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
import org.inlighting.oj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/leaderboard", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @ApiOperation("获取某个比赛的排行榜")
    @GetMapping("/contest/{cid}")
    @SuppressWarnings("unchecked")
    public ResponseEntity getContestLeaderboard(@PathVariable int cid) {
        List<Map<String, Object>> list = leaderboardService.getContestLeaderboard(cid);
        if (list == null) {
            throw new WebErrorException("不存在此比赛");
        }
        List<Map<String, Object>> targetList = new LinkedList<>(list);
        targetList.remove(1);
        return new ResponseEntity(targetList);
    }

    @ApiOperation("获取网站比赛排行榜")
    @GetMapping
    public ResponseEntity getLeaderboard(@RequestParam("page") int page,
                                         @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, leaderboardService.getLeaderboard(pager)));
    }
}
