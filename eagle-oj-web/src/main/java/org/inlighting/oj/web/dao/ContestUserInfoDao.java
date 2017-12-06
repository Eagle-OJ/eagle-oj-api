package org.inlighting.oj.web.dao;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestUserInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author = ygj
 **/
@Repository
public class ContestUserInfoDao {

    public boolean add(SqlSession sqlSession, ContestUserInfoEntity contestUserInfoEntity){
        int insertNum = sqlSession.insert("contestUserInfo.add",contestUserInfoEntity);
        return insertNum == 1;
    }

    public ContestUserInfoEntity getByUidAndUid(SqlSession sqlSession, ContestUserInfoEntity entity) {
        return sqlSession.selectOne("contestUserInfo.getByUidAndUid", entity);
    }

    public boolean updateData(SqlSession sqlSession, int cid, int uid, int submitTimes,
                           int acceptTimes, long newlyAcceptTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("uid", uid);
        map.put("submitTimes", submitTimes);
        map.put("acceptTimes", acceptTimes);
        map.put("newlyAcceptTime", newlyAcceptTime);
        return sqlSession.update("contestUserInfo.addData", map) == 1;
    }
}
