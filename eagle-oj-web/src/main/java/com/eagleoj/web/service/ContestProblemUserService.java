package com.eagleoj.web.service;

import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.entity.ContestProblemUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ContestProblemUserService {

    ContestProblemUserEntity getByCidPidUid(int cid, int pid, int uid);

    List<ContestProblemUserEntity> listAllByCid(int cid);

    List<Map<String, Object>> listUserDetailInContest(int cid, int uid);

    void save(int cid, int pid, int uid, int score,
                 ResultEnum status, long solvedTimes, long usedTime);

    boolean update(int cid, int pid, int uid, int score, ResultEnum status,
                   long solvedTime, long usedTime);

}
