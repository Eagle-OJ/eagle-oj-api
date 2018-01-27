package com.eagleoj.web.service;

import com.eagleoj.web.entity.ProblemModeratorEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ProblemModeratorService {

    void addProblemModerator(int pid, String email);

    List<Map<String, Object>> listProblemModerators(int pid);

    void deleteModerator(int pid, int uid);

    boolean isExistModeratorInProblem(int pid, int uid);
}
