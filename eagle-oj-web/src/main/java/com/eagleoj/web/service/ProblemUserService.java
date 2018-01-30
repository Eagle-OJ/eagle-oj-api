package com.eagleoj.web.service;

import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.entity.ProblemUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ProblemUserService {

    void save(int pid, int uid, ResultEnum status);

    ProblemUserEntity get(int pid, int uid);

    List<Map<String, Object>> listUserProblemHistory(int uid);

    void updateByPidUid(int pid, int uid, ResultEnum result);
}
