package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.inlighting.oj.web.dao.ProblemDao;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class ProblemService {

    private ProblemDao problemDao;



    @Autowired
    public void setProblemDao(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    /**
     * 同时添加problem 和 problem_info
     */
    public boolean addProblem(int pid,
                              int owner,
                              JSONArray codeLanguage,
                              String title,
                              String description,
                              int difficult,
                              String inputFormat,
                              String outputFormat,
                              String constraint,
                              JSONArray sample,
                              JSONArray moderator,
                              JSONArray tag,
                              int share) {
        // todo
        return false;
    }

    public ProblemEntity getProblemByPid(int pid) {
        // todo
        return null;
    }

    public boolean updateProblem(int pid, ProblemEntity entity) {
        // todo
        return false;
    }
}
