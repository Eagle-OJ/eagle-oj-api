package com.eagleoj.web.service;

import com.eagleoj.web.entity.ProblemModeratorEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ProblemModeratorService {

    boolean save(int pid, int uid);

    boolean isExistModeratorInProblem(int pid, int uid);

    ProblemModeratorEntity get(int pid, int uid);

    List<Map<String, Object>> listProblemModerators(int pid);

    boolean delete(int pid, int uid);
}
