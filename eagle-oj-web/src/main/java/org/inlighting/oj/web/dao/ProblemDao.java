package org.inlighting.oj.web.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.data.DataHelper;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class ProblemDao {

    public static void main(String[] args) {
        SqlSession session = DataHelper.getSession();
        PageRowBounds bounds = new PageRowBounds(1, 2);

        List<ProblemEntity> list = session.selectList("problem.getAllProblem", null, bounds);

        list.forEach(entity -> System.out.println(entity.getTitle()+";"+entity.getPid()));
    }

    public List<ProblemEntity> getAll(PageRowBounds bounds) {
        SqlSession session = DataHelper.getSession();
        List<ProblemEntity> list = session.selectList("problem.getAllProblem", null, bounds);
        return list;
    }


    public boolean addProblem(SqlSession sqlSession, ProblemEntity problemEntity) {
        // 添加问题
        int insertNum = sqlSession.insert("problem.insertProblem",problemEntity);
        if(insertNum == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public ProblemEntity getProblemByPid(SqlSession sqlSession,int pid) {
        // 根据ID查找题目
        ProblemEntity problemEntity =  sqlSession.selectOne("problem.getProblemByPid",pid);
        return problemEntity;
    }

    public boolean updateProblemByPid(SqlSession sqlSession, ProblemEntity entity) {
        int updateNum = sqlSession.update("problem.updateProblemByPid",entity);
        if(updateNum == 1)
            return true;
        else {
            return false;
        }
    }

}
