package org.inlighting.oj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.ResultEnum;
import org.inlighting.oj.web.dao.ContestProblemDao;
import org.inlighting.oj.web.dao.SubmissionDao;
import org.inlighting.oj.web.dao.UserDao;
import org.inlighting.oj.web.entity.SubmissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Smith
 **/
@Service
public class SubmissionService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private SubmissionDao submissionDao;

    public int add(int owner, int pid, int cid, int sourceCode, LanguageEnum lang, double time, int memory,
                   ResultEnum status) {
        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setOwner(owner);
        submissionEntity.setPid(pid);
        submissionEntity.setCid(cid);
        submissionEntity.setSourceCode(sourceCode);
        submissionEntity.setLang(lang);
        submissionEntity.setTime(time);
        submissionEntity.setMemory(memory);
        submissionEntity.setStatus(status);
        submissionEntity.setSubmitTime(System.currentTimeMillis());
        return submissionDao.insert(sqlSession, submissionEntity) ? submissionEntity.getSid(): 0;
    }

    public List<HashMap<String, Object>> getSubmissions(int owner, int cid, int pid, PageRowBounds pager) {
        SubmissionEntity entity = new SubmissionEntity();
        entity.setOwner(owner);
        entity.setCid(cid);
        entity.setPid(pid);
        return submissionDao.getSubmissions(sqlSession, entity, pager);
    }
}
