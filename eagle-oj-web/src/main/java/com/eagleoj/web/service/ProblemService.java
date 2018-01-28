package com.eagleoj.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.entity.ProblemEntity;

import java.util.List;
import java.util.Map;

/**
 * @author ygj
 **/
public interface ProblemService {

    int save(JSONArray tags, int owner, String title, JSONObject description, JSONObject inputFormat, JSONObject outputFormat,
             int difficult, JSONArray samples, int time, int memory);

    void updateProblem(int pid, JSONArray tags, ProblemEntity problemEntity);

    void updateProblem(int pid, ProblemEntity problemEntity);

    List<ProblemEntity> listUserProblems(int uid);

    List<ProblemEntity> listAuditingProblems();

    void acceptProblem(ProblemEntity problemEntity);

    void refuseProblem(ProblemEntity problemEntity);

    ProblemEntity getProblem(int pid);

    List<Map<String, Object>> listProblemTags(int pid);

    List<Map<String, Object>> listSharedProblems(String tag, Integer difficult, Integer uid);

}