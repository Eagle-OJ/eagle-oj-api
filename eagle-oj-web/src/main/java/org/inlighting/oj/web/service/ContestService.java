package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestDao;
import org.inlighting.oj.web.entity.ContestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class ContestService {

    private final SqlSession sqlSession;

    private ContestDao contestDao ;

    public ContestService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setContestDao(ContestDao contestDao) {
        this.contestDao = contestDao;
    }

    public int addContest(String name, int owner, String slogan, String description,
                          long startTime, long endTime, long totalTime, String password, int official,
                          int type, int status, long createTime) {
        // 添加比赛
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
        contestEntity.setStatus(status);
        contestEntity.setCreateTime(createTime);
        boolean result = contestDao.addContest(sqlSession,contestEntity);
        return result ? contestEntity.getCid() : 0;
    }

    // 进行比赛校验
    public ContestEntity getContestByCid(int cid, boolean valid) {
        ContestEntity contestEntity = contestDao.getContestByCid(sqlSession, cid);
        if (valid) {
            if (contestEntity.getStatus()==1 && contestEntity.getEndTime()<System.currentTimeMillis()) {
                contestEntity.setStatus(0);
                updateContestByCid(cid, contestEntity);
                return contestEntity;
            }
        }
        return contestEntity;
    }

    // 不进行比赛校验
    public ContestEntity getContestByCid(int cid) {
        return getContestByCid(cid, false);
    }

    public boolean deleteContestByCid(int cid){
        //根据比赛ID来删除比赛
        return contestDao.deleteContestByCid(sqlSession,cid);
    }

    public boolean updateContestByCid(int cid, ContestEntity contestEntity) {
        return contestDao.updateContestByCid(sqlSession, contestEntity);
    }

    public List<ContestEntity> getAll(){
        //获得所有比赛
        return contestDao.getAll(sqlSession);
    }

}
