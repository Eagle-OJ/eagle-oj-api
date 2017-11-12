package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.GroupUserInfoDao;
import org.inlighting.oj.web.entity.GroupEntity;
import org.inlighting.oj.web.entity.GroupUserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class GroupUserInfoService {

    private GroupUserInfoDao groupUserInfoDao;

    private SqlSession sqlSession;

    public GroupUserInfoService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setGroupUserInfoDao(GroupUserInfoDao groupUserInfoDao) {
        this.groupUserInfoDao = groupUserInfoDao;
    }

    public boolean add(int gid, int uid, long joinTime) {
        // 添加
        GroupUserInfoEntity groupUserInfoEntity = new GroupUserInfoEntity();
        groupUserInfoEntity.setGid(gid);
        groupUserInfoEntity.setUid(uid);
        groupUserInfoEntity.setJoinTime(joinTime);
        return groupUserInfoDao.addGroupUserInfo(sqlSession,groupUserInfoEntity);
    }

    public GroupUserInfoEntity getByGidAndUid(int gid, int uid) {
        // 根据Uid和Gid获取group
        Map<String,Object> map = new HashMap<>();
        map.put("gid",gid);
        map.put("uid",uid);
        return groupUserInfoDao.getByGidAndUid(sqlSession,map);
    }

    public boolean deleteByGidAndUid(int gid, int uid) {
        //删除
        Map<String,Object> map = new HashMap<>();
        map.put("gid",gid);
        map.put("uid",uid);
        return groupUserInfoDao.deleteByGidAndUid(sqlSession,map);
    }
}
