package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ProblemDao;
import org.inlighting.oj.web.dao.ProblemInfoDao;
import org.inlighting.oj.web.data.DataHelper;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.ProblemInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ygj
 **/
@Service
public class ProblemService {

    private ProblemDao problemDao;

    private ProblemInfoDao problemInfoDao;

    @Autowired
    public void setProblemDao(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    @Autowired
    public void setProblemInfoDao(ProblemInfoDao problemInfoDao) {
        this.problemInfoDao = problemInfoDao;
    }

    /**
     * 同时添加problem 和 problem_info
     */
    public boolean addProblem( int owner, JSONArray codeLanguage, String title,
                              String description, int difficult, String inputFormat,
                              String outputFormat, String constraint, JSONArray sample,
                              JSONArray moderator, JSONArray tag, int share, long create_time) {
        // 添加题目
        SqlSession sqlSession = DataHelper.getSession(true);
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setOwner(owner);
        problemEntity.setCodeLanguage(codeLanguage);
        problemEntity.setTitle(title);
        problemEntity.setDescription(description);
        problemEntity.setDifficult(difficult);
        problemEntity.setInputFormat(inputFormat);
        problemEntity.setOutputFormat(outputFormat);
        problemEntity.setConstraint(constraint);
        problemEntity.setSample(sample);
        problemEntity.setModerator(moderator);
        problemEntity.setTag(tag);
        problemEntity.setShare(share);
        problemEntity.setCreateTime(create_time);

        // 添加数据到problem_info
        boolean result1 = problemDao.addProblem(sqlSession, problemEntity);
        boolean result2 = false;
        int pid = problemEntity.getPid();
        if (pid > 0) {
            ProblemInfoEntity entity = new ProblemInfoEntity();
            entity.setPid(pid);
            entity.setBelong(0);
            result2 = problemInfoDao.add(sqlSession, entity);
        }
        sqlSession.commit();
        sqlSession.close();
        return result1 && result2;
    }

    public ProblemEntity getProblemByPid(int pid) {
        // todo
        // 通过ID获得题目
        SqlSession sqlSession = DataHelper.getSession();
        sqlSession.close();
        return problemDao.getProblemByPid(sqlSession, pid);
    }

    public boolean updateProblemById(ProblemEntity entity) {
        //
        SqlSession sqlSession = DataHelper.getSession();
        sqlSession.close();
        return  problemDao.updateProblemByPid(sqlSession,entity);
    }
}
