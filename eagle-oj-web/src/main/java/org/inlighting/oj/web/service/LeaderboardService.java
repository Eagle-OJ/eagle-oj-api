package org.inlighting.oj.web.service;

import org.ehcache.Cache;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.entity.ContestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class LeaderboardService {

    private ContestProblemUserService contestProblemUserService;

    private ContestService contestService;

    @Autowired
    public void setContestProblemUserService(ContestProblemUserService contestProblemUserService) {
        this.contestProblemUserService = contestProblemUserService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getContestLeaderboard(int cid) {
        ContestEntity contestEntity = contestService.getContestByCid(cid);
        if (contestEntity == null) {
            return null;
        }
        int type = contestEntity.getType();
        Cache<Integer, Object> leaderboard = CacheController.getLeaderboard();
        List<Map<String, Object>> list = (List<Map<String, Object>>) leaderboard.get(cid);
        if (type == 0 || type == 1) {
            // 普通比赛
            if (list == null) {
                list = contestProblemUserService.getNormalContestRank(cid);
                Map<String, Object> meta = new HashMap<>(2);
                meta.put("total", list.size());
                meta.put("create_time", System.currentTimeMillis());
                list.add(0, meta);
                leaderboard.put(cid, list);
            }
        } else {
            if (list == null) {

            }
        }
        return list;
    }

    public Map<String, Object> getUserMetaInContest(int uid, int cid) {
        Map<String, Object> meta = new HashMap<>(3);
        List<Map<String, Object>> list = getContestLeaderboard(cid);
        int rank = 1;
        for (int i=1; i<list.size(); i++) {
            if ((long)list.get(i).get("uid") == uid) {
                break;
            } else {
                rank++;
            }
        }
        meta.put("rank", rank);
        meta.put("create_time", list.get(0).get("create_time"));
        meta.put("total", list.get(0).get("total"));
        return meta;
    }

}
