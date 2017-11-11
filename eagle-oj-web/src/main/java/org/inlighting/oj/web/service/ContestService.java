package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestDao;
import org.inlighting.oj.web.data.DataHelper;
import org.inlighting.oj.web.entity.ContestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author Smith
 **/
@Service
public class ContestService {
    private ContestDao contestDao ;

    @Autowired
    public void setContestDao(ContestDao contestDao) {
        this.contestDao = contestDao;
    }

    public int addContest(String name, int owner, String slogan, String description,
                          long startTime, long endTime, long totalTime, String password, int official,
                          int type, long createTime) {
        // 添加比赛
        SqlSession sqlSession = DataHelper.getSession();
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setName(name);
        contestEntity.setOwner(owner);
        contestEntity.setSlogan(slogan);
        contestEntity.setDescription(description);
        contestEntity.setStartTime(startTime);
        contestEntity.setEndTime(endTime);
        contestEntity.setTotalTime(totalTime);
        contestEntity.setPassword(password);
        contestEntity.setOfficial(official);
        contestEntity.setType(type);
        contestEntity.setCreateTime(createTime);
        boolean result = contestDao.addContest(sqlSession,contestEntity);
        sqlSession.close();
        return result ? contestEntity.getCid() : 0;
    }

    public ContestEntity getContestByCid(int cid) {
        //通过Cid获取比赛
        SqlSession sqlSession = DataHelper.getSession();
        ContestEntity contestEntity = contestDao.getContestByCid(sqlSession,cid);
        sqlSession.close();
        return contestEntity;
    }

    public boolean deleteContestByCid(int cid){
        //根据比赛ID来删除比赛
        SqlSession sqlSession = DataHelper.getSession();
        boolean flag = contestDao.deleteContestByCid(sqlSession,cid);
        sqlSession.close();
        return flag;
    }

    public List<ContestEntity> getAll(){
        //获得所有比赛
        SqlSession sqlSession = DataHelper.getSession();
        List<ContestEntity> list = contestDao.getAll(sqlSession);
        sqlSession.close();
        return list;
    }


}
