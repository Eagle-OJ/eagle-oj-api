package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestUserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author = ygj
 **/
@Repository
public class ContestUserDao {

    public boolean add(SqlSession sqlSession, ContestUserEntity contestUserInfoEntity){
        int insertNum = sqlSession.insert("contestUser.add",contestUserInfoEntity);
        return insertNum == 1;
    }

    public ContestUserEntity getByUidAndUid(SqlSession sqlSession, ContestUserEntity entity) {
        return sqlSession.selectOne("contestUser.getByUidAndUid", entity);
    }

    public boolean updateData(SqlSession sqlSession, int cid, int uid, int submitTimes,
                           int acceptTimes, long newlyAcceptTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("uid", uid);
        map.put("submitTimes", submitTimes);
        map.put("acceptTimes", acceptTimes);
        map.put("newlyAcceptTime", newlyAcceptTime);
        return sqlSession.update("contestUser.addData", map) == 1;
    }
}
