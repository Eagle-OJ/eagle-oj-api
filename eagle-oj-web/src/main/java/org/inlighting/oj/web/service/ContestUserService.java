package org.inlighting.oj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestUserDao;
import org.inlighting.oj.web.entity.ContestUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestUserService {

    private final SqlSession sqlSession;

    private ContestUserDao contestUserDao;

    public ContestUserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setContestUserDao(ContestUserDao contestUserDao) {
        this.contestUserDao = contestUserDao;
    }

    /**
     * 联合主键（cid,uid），所以没有自增主键
     */
    public boolean add(int cid, int uid, long joinTime) {
        //添加contestUserInfo
        ContestUserEntity contestUserEntity = new ContestUserEntity();
        contestUserEntity.setCid(cid);
        contestUserEntity.setUid(uid);
        contestUserEntity.setJoinTime(joinTime);
        return contestUserDao.add(sqlSession, contestUserEntity);
    }

    public ContestUserEntity get(int cid, int uid) {
        // 通过cid和uid来获取实体
        ContestUserEntity entity = new ContestUserEntity();
        entity.setCid(cid);
        entity.setUid(uid);
        return contestUserDao.get(sqlSession, entity);
    }

    public List<Map<String, Object>> getUserContests(int uid, PageRowBounds pager) {
        ContestUserEntity entity = new ContestUserEntity();
        return contestUserDao.getUserContests(sqlSession, uid, pager);
    }

    public List<Map<String, Object>> getNormalContestRankList(int cid) {
        return contestUserDao.getNormalContestRankList(sqlSession, cid);
    }

    public List<Map<String, Object>> getACMContestRankList(int cid, int penalty) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("cid", cid);
        param.put("penalty", penalty);
        return contestUserDao.getACMContestRankList(sqlSession, param);
    }

    public boolean updateTimesAndData(int cid, int uid, ContestUserEntity entity) {
        entity.setCid(cid);
        entity.setUid(uid);
        return contestUserDao.updateTimesAndData(sqlSession, entity);
    }
}
