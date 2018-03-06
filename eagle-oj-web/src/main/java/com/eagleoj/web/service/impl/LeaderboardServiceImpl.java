package com.eagleoj.web.service.impl;

import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.dao.LeaderboardMapper;
import com.eagleoj.web.dao.UserMapper;
import com.eagleoj.web.data.status.ContestTypeStatus;
import com.eagleoj.web.data.status.RoleStatus;
import com.eagleoj.web.entity.ContestEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.ContestProblemUserService;
import com.eagleoj.web.service.ContestService;
import com.eagleoj.web.service.LeaderboardService;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    @Autowired
    private ContestProblemUserService contestProblemUserService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LeaderboardMapper leaderboardMapper;

    @Override
    public void refreshContestLeaderboard(int cid) {
        Cache<Integer, Object> leaderboard = CacheController.getLeaderboard();
        leaderboard.remove(cid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getContestLeaderboard(int cid) {
        ContestEntity contestEntity = contestService.getContest(cid);
        int type = contestEntity.getType();
        int gid = contestEntity.getGroup();
        boolean isACM = false;
        Cache<Integer, Object> leaderboard = CacheController.getLeaderboard();
        List<Map<String, Object>> list = (List<Map<String, Object>>) leaderboard.get(cid);
        if (list!=null) {
            return list;
        }
        if (type == ContestTypeStatus.OI_CONTEST_NORMAL_TIME.getNumber()
                || type == ContestTypeStatus.OI_CONTEST_LIMIT_TIME.getNumber()) {
            // OI比赛
            list = leaderboardMapper.listOIRankByCid(cid, gid,contestEntity.getOwner());
        } else {
            // ACM
            list = leaderboardMapper.listACMRankByCid(cid,DefaultConfig.ACM_PENALTY_TIME, gid,contestEntity.getOwner());
            isACM = true;
        }
        // 存放本次生成的基本信息
        Map<String, Object> meta = new HashMap<>(2);
        meta.put("name", contestEntity.getName());
        meta.put("is_acm", isACM);
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

    @Override
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

    @Override
    public List<UserEntity> getLeaderboard() {
        return userMapper.listUserRank(RoleStatus.ADMIN.getNumber(), RoleStatus.ROOT.getNumber());
    }

    @Override
    public List<Map<String, Object>> getUserDetailInContest(int cid, int uid) {
        return contestProblemUserService.listUserDetailInContest(cid, uid);
    }
}
