package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestUserInfoDao;
import org.inlighting.oj.web.data.DataHelper;
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

    /**
     * 联合主键（cid,uid），所以没有自增主键
     */

    private ContestUserInfoDao contestUserInfoDao;

    @Autowired
    public void setContestUserInfoDao(ContestUserInfoDao contestUserInfoDao) {
        this.contestUserInfoDao = contestUserInfoDao;
    }

    public boolean add(int cid, int uid) {
        //添加contestUserInfo
        SqlSession sqlSession = DataHelper.getSession();
         ContestUserInfoEntity contestUserInfoEntity = new ContestUserInfoEntity();
         contestUserInfoEntity.setCid(cid);
         contestUserInfoEntity.setUid(uid);
         boolean flag = contestUserInfoDao.add(sqlSession,contestUserInfoEntity);
         sqlSession.close();
         return flag;
    }

    public ContestUserInfoEntity getByCidAndUid(int cid, int uid) {
        // 通过cid和uid来获取实体
        SqlSession sqlSession = DataHelper.getSession();
        Map<String,Object> map = new HashMap<>();
        map.put("cid",cid);
        map.put("uid",uid);
        ContestUserInfoEntity contestUserInfoEntity = contestUserInfoDao.getByUidAndUid(sqlSession,map);
        sqlSession.close();
        return contestUserInfoEntity;
    }


}
