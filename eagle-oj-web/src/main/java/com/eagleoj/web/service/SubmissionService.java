package com.eagleoj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.SubmissionDao;
import com.eagleoj.web.entity.SubmissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface SubmissionService {

    int save(int owner, int pid, int cid, int sourceCode, LanguageEnum lang, double time, int memory,
             ResultEnum status);

    List<Map<String, Object>> listSubmissions(int owner, int pid, int cid);
}
