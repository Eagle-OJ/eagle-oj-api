package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ContestProblemUserDao;
import com.eagleoj.web.entity.ContestProblemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ContestProblemUserService {

    ContestProblemUserEntity getByCidPidUid(int cid, int pid, int uid);

    List<ContestProblemUserEntity> listAllByCid(int cid);

    List<Map<String, Object>> listNormalContestRankByCid(int cid);

    boolean save(int cid, int pid, int uid, int score,
                 ResultEnum status, long solvedTimes, long usedTime);

    boolean update(int cid, int pid, int uid, int score, ResultEnum status,
                   long solvedTime, long usedTime);

}
