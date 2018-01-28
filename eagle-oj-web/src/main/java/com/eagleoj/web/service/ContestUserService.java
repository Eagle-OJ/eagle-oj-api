package com.eagleoj.web.service;

import com.eagleoj.web.entity.ContestUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ContestUserService {

    ContestUserEntity get(int cid, int uid);

    List<Map<String, Object>> listUserContests(int uid);

    List<Map<String, Object>> listNormalContestRank(int cid);

    List<Map<String, Object>> listACMContestRank(int cid, int penalty);

    boolean updateByCidUid(int cid, int uid, ContestUserEntity entity);

    void joinContest(int cid, int uid, String password);
}
