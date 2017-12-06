package org.inlighting.oj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestDao;
import org.inlighting.oj.web.entity.ContestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
                          long startTime, long endTime, Long totalTime, String password,
                          int type, long createTime) {
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
        contestEntity.setOfficial(0);
        contestEntity.setType(type);
        contestEntity.setStatus(0);
        contestEntity.setCreateTime(createTime);
        boolean result = contestDao.addContest(sqlSession,contestEntity);
        return result ? contestEntity.getCid() : 0;
    }

    public List<ContestEntity> getUserContests(int uid, PageRowBounds pager) {
        return contestDao.getUserContests(sqlSession, uid, pager);
    }

    // 进行比赛校验
    public ContestEntity getContestByCid(int cid) {
        ContestEntity contestEntity = contestDao.getContestByCid(sqlSession, cid);
        checkContestValid(contestEntity);
        return contestEntity;
    }

    public boolean deleteContestByCid(int cid){
        //根据比赛ID来删除比赛
        return contestDao.deleteContestByCid(sqlSession,cid);
    }

    public boolean updateContestDescription(int cid, String name, String slogan, String description, long startTime,
                                            long endTime, Long totalTime, String password, int type) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setCid(cid);
        contestEntity.setName(name);
        contestEntity.setSlogan(slogan);
        contestEntity.setDescription(description);
        contestEntity.setStartTime(startTime);
        contestEntity.setEndTime(endTime);
        contestEntity.setTotalTime(totalTime);
        contestEntity.setPassword(password);
        contestEntity.setType(type);
        return contestDao.updateContestDescription(sqlSession, contestEntity);
    }

    public List<HashMap<String, Object>> getValidContests(PageRowBounds pager){
        List<HashMap<String, Object>> contests = contestDao.getValidContests(sqlSession, pager);
        for (HashMap<String, Object> contest: contests) {
            checkContestValid(contest);
        }
        return contests;
    }

    private void checkContestValid(ContestEntity contestEntity) {
        if (contestEntity.getStatus() == 1) {
            if (contestEntity.getEndTime()<System.currentTimeMillis()) {
                closeContest(contestEntity.getCid());
                contestEntity.setStatus(2);
            }
        }
    }

    private void checkContestValid(HashMap<String, Object> contest) {
        if ((int)contest.get("status") == 1) {
            if ((long)contest.get("endTime")<System.currentTimeMillis()) {
                closeContest((int)contest.get("cid"));
                contest.replace("status", 2);
            }
        }
    }

    public boolean closeContest(int cid) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setCid(cid);
        contestEntity.setStatus(2);
        return contestDao.updateContestStatus(sqlSession, contestEntity);
    }
}
