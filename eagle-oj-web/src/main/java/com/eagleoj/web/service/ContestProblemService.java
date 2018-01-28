package com.eagleoj.web.service;

import com.eagleoj.web.entity.ContestProblemEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ContestProblemService {

    ContestProblemEntity getContestProblem(int cid, int pid);

    List<Map<String, Object>> listContestProblems(int cid);

    List<Map<String, Object>> listContestProblems (int cid, int uid);

    void saveContestProblem(int cid, int pid, int displayId, int score);

    boolean updateContestProblemInfo(int cid, int pid, int displayId, int score);

    boolean updateContestProblemTimes(int cid, int pid, ContestProblemEntity entity);

    boolean deleteByCidPid(int cid, int pid);

}
