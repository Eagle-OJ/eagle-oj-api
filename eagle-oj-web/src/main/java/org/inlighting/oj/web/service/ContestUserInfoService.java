package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestUserInfoDao;
import org.inlighting.oj.web.entity.ContestUserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestUserInfoService {

    private final SqlSession sqlSession;

    private ContestUserInfoDao contestUserInfoDao;

    public ContestUserInfoService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setContestUserInfoDao(ContestUserInfoDao contestUserInfoDao) {
        this.contestUserInfoDao = contestUserInfoDao;
    }

    /**
     * 联合主键（cid,uid），所以没有自增主键
     */
    public boolean add(int cid, int uid, long joinTime) {
        //添加contestUserInfo
        ContestUserInfoEntity contestUserInfoEntity = new ContestUserInfoEntity();
        contestUserInfoEntity.setCid(cid);
        contestUserInfoEntity.setUid(uid);
        contestUserInfoEntity.setJoinTime(joinTime);
        return contestUserInfoDao.add(sqlSession,contestUserInfoEntity);
    }

    public ContestUserInfoEntity getByCidAndUid(int cid, int uid) {
        // 通过cid和uid来获取实体
        ContestUserInfoEntity entity = new ContestUserInfoEntity();
        entity.setCid(cid);
        entity.setUid(uid);
        return contestUserInfoDao.getByUidAndUid(sqlSession, entity);
    }

    public boolean updateData(int cid, int uid, int submitTimes, int acceptTimes, long newlyAcceptTime) {
        return contestUserInfoDao.updateData(sqlSession, cid, uid, submitTimes, acceptTimes, newlyAcceptTime);
    }
}
