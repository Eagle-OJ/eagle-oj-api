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

    int save(int owner, String title, JSONObject description, JSONObject inputFormat, JSONObject outputFormat,
             int difficult, JSONArray samples, int time, int memory);

    List<ProblemEntity> listUserProblems(int uid);

    List<ProblemEntity> listAuditingProblems();

    boolean refuseProblem(int pid);

    boolean acceptProblem(int pid);

    ProblemEntity getProblem(int pid);

    List<Map<String, Object>> listProblemTags(int pid);

    List<Map<String, Object>> listSharedProblems(int difficult, String tag);

    List<Map<String, Object>> listSharedProblemsWithUserStatus(int uid, int difficult, String tag);

    boolean updateProblemDescriptionByPid(int pid, String title, JSONObject description, JSONObject inputFormat,
                                  JSONObject outputFormat, JSONArray samples, int difficult);

    boolean updateProblemSettingByPid(int pid, JSONArray lang, int time, int memory, int status);

    boolean updateProblemTimesByPid(int pid, ProblemEntity problemEntity);
}
