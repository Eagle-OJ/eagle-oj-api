package com.eagleoj.web.service;

import com.eagleoj.web.entity.ProblemModeratorEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ProblemModeratorService {

    void addProblemModerator(int pid, String email);

    int countProblemModerators(int pid);

    List<Map<String, Object>> listProblemModerators(int pid);

    void deleteModerator(int pid, int uid);

    void deleteModerators(int pid);

    boolean isExistModeratorInProblem(int pid, int uid);
}
