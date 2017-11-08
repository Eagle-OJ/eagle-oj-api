package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ProblemDao;
import org.inlighting.oj.web.data.DataHelper;
import org.inlighting.oj.web.entity.ProblemEntity;
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

    private ProblemDao problemDao  = new ProblemDao();



    @Autowired
    public void setProblemDao(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    /**
     * 同时添加problem 和 problem_info
     */
    public boolean addProblem( int owner, JSONArray codeLanguage, String title,
                              String description, int difficult, String inputFormat,
                              String outputFormat, String constraint, JSONArray sample,
                              JSONArray moderator, JSONArray tag, int share,long create_time) {
        // 添加题目
        SqlSession sqlSession = DataHelper.getSession();
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
        problemEntity.setCreateTime(System.currentTimeMillis());
        if(problemDao.addProblem(sqlSession,problemEntity)) {
            sqlSession.close();
            return true;
        }
        else {
            sqlSession.close();
            return false;
        }
    }

    public ProblemEntity getProblemByPid(int pid) {
        // 通过ID获得题目
        SqlSession sqlSession = DataHelper.getSession();
        sqlSession.close();
        return problemDao.getProblemByPid(sqlSession,1);
    }

    public boolean updateProblemById(ProblemEntity entity) {
        //
        SqlSession sqlSession = DataHelper.getSession();
        sqlSession.close();
        return  problemDao.updateProblemByPid(sqlSession,entity);
    }

    public static void main(String[] args) {
        ProblemService  problemService = new ProblemService();
//        System.out.println(problemService.getProblemByPid(1));
        int owner = 66;
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        JSONArray language = new JSONArray(list);
        String title = "test";
        String description = "test description";
        int difficult = 1;
        String input_format = "test input_format";
        String output_format = "test output_format";
        String constraint = "test constraint";
        ArrayList<Object> samples = new ArrayList<>();
        samples.add(1);
        samples.add(2);
        JSONArray sampleArray = new JSONArray(samples);
        ArrayList<Object> moderators = new ArrayList<>();
        moderators.add(1);
        moderators.add(2);
        JSONArray modeArray = new JSONArray(moderators);
        ArrayList<Object> tags = new ArrayList<>();
        tags.add(1);
        tags.add(2);
        JSONArray tagArray = new JSONArray(tags);
        int share = 1;
        long create_time = System.currentTimeMillis();
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setPid(2);
        problemEntity.setOwner(owner);
        problemEntity.setCodeLanguage(language);
        problemEntity.setTitle(title);
        problemEntity.setDescription(description);
        problemEntity.setDifficult(difficult);
        problemEntity.setInputFormat(input_format);
        problemEntity.setOutputFormat(output_format);
        problemEntity.setConstraint(constraint);
        problemEntity.setSample(sampleArray);
        problemEntity.setModerator(modeArray);
        problemEntity.setTag(tagArray);
        problemEntity.setShare(share);
        problemEntity.setCreateTime(System.currentTimeMillis());
        problemService.updateProblemById(problemEntity);
//        problemService.addProblem(owner, language,title,description,difficult,input_format,output_format,
//                constraint,sampleArray,modeArray,tagArray,share,create_time);
    }
}
