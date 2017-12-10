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

    private ContestUserDao contestUserInfoDao;

    public ContestUserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setContestUserInfoDao(ContestUserDao contestUserInfoDao) {
        this.contestUserInfoDao = contestUserInfoDao;
    }

    /**
     * 联合主键（cid,uid），所以没有自增主键
     */
    public boolean add(int cid, int uid, long joinTime) {
        //添加contestUserInfo
        ContestUserEntity contestUserInfoEntity = new ContestUserEntity();
        contestUserInfoEntity.setCid(cid);
        contestUserInfoEntity.setUid(uid);
        contestUserInfoEntity.setJoinTime(joinTime);
        return contestUserInfoDao.add(sqlSession,contestUserInfoEntity);
    }

    public ContestUserEntity getByCidAndUid(int cid, int uid) {
        // 通过cid和uid来获取实体
        ContestUserEntity entity = new ContestUserEntity();
        entity.setCid(cid);
        entity.setUid(uid);
        return contestUserInfoDao.getByUidAndUid(sqlSession, entity);
    }

    public boolean updateData(int cid, int uid, int submitTimes, int acceptTimes, long newlyAcceptTime) {
        return contestUserInfoDao.updateData(sqlSession, cid, uid, submitTimes, acceptTimes, newlyAcceptTime);
    }
}
