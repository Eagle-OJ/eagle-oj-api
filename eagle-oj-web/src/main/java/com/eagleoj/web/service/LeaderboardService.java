package com.eagleoj.web.service;

import com.eagleoj.web.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface LeaderboardService {

    void refreshContestLeaderboard(int cid);

    List<Map<String, Object>> getContestLeaderboard(int cid);

    Map<String, Object> getUserMetaInContest(int uid, int cid);

    List<UserEntity> getLeaderboard();
}
