package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSON;
import org.ehcache.Cache;
import org.inlighting.oj.web.DefaultConfig;
import org.inlighting.oj.web.cache.CacheController;
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

    private ContestUserService contestUserService;

    private ContestService contestService;

    @Autowired
    public void setContestUserService(ContestUserService contestUserService) {
        this.contestUserService = contestUserService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    public void refreshContestLeaderboard(int cid) {
        Cache<Integer, Object> leaderboard = CacheController.getLeaderboard();
        leaderboard.remove(cid);
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

        if (list!=null) {
            return list;
        }
        if (type == 0 || type == 1) {
            // 普通比赛
            list = contestUserService.getNormalContestRankList(cid);
        } else {
            // ACM
            list = contestUserService.getACMContestRankList(cid, DefaultConfig.ACM_PENALTY_TIME);
        }
        // 存放本次生成的基本信息
        Map<String, Object> meta = new HashMap<>(2);
        meta.put("name", contestEntity.getName());
        meta.put("cid", contestEntity.getCid());
        meta.put("total", list.size());
        meta.put("create_time", System.currentTimeMillis());
        list.add(0, meta);

        // 存放用户的排名信息
        Map<String, Object> userRank = new HashMap<>(list.size());
        for (int nowRank = 1; nowRank<list.size(); nowRank++) {
            Map<String, Object> temp = list.get(nowRank);
            userRank.put(String.valueOf(temp.get("uid")), nowRank);
        }
        list.add(1, userRank);

        leaderboard.put(cid, list);
        return list;
    }

    public Map<String, Object> getUserMetaInContest(int uid, int cid) {
        Map<String, Object> meta = new HashMap<>(3);
        List<Map<String, Object>> list = getContestLeaderboard(cid);
        Map<String, Object> rankMeta = list.get(0);
        Map<String, Object> userRank = list.get(1);
        int rank = -1;
        if (userRank.containsKey(String.valueOf(uid))) {
            rank = (int) userRank.get(String.valueOf(uid));
        }
        meta.put("rank", rank);
        meta.put("create_time", rankMeta.get("create_time"));
        meta.put("total", rankMeta.get("total"));
        return meta;
    }


}
