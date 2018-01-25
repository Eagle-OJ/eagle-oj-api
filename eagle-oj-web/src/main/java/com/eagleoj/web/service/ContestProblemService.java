package com.eagleoj.web.service;

import com.eagleoj.web.entity.ContestProblemEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ContestProblemService {

    ContestProblemEntity getContestProblem(int cid, int pid);

    List<Map<String, Object>> listContestProblemsByCid(int cid);

    List<Map<String, Object>> listContestProblemsWithUserStatus (int cid, int uid);

    boolean save(int pid, int cid, int displayId, int score);

    boolean updateContestProblemInfo(int cid, int pid, int displayId, int score);

    boolean updateContestProblemTimes(int cid, int pid, ContestProblemEntity entity);

    boolean deleteByCidPid(int cid, int pid);

    boolean displayIdIsDuplicate(int cid, int displayId);
}
