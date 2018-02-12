package com.eagleoj.web.service;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface SubmissionService {

    void save(int owner, int pid, int cid, int gid, int sourceCode, LanguageEnum lang, double time, int memory,
             ResultEnum status);

    int countProblemSubmissions(int pid);

    List<Map<String, Object>> listOwnSubmissions(Integer owner, Integer pid, Integer cid);

    List<Map<String, Object>> listProblemSubmissions(Integer pid, Integer cid);
}
