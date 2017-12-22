package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestUserDao;
import org.inlighting.oj.web.entity.ContestUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean updateTimesAndData(int cid, int uid, ContestUserEntity entity) {
        entity.setCid(cid);
        entity.setUid(uid);
        return contestUserDao.updateTimesAndData(sqlSession, entity);
    }
}
