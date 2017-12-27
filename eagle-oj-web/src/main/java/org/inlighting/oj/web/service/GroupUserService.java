package org.inlighting.oj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.GroupUserDao;
import org.inlighting.oj.web.entity.GroupUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class GroupUserService {

    private GroupUserDao groupUserDao;

    private SqlSession sqlSession;

    public GroupUserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setGroupUserDao(GroupUserDao groupUserDao) {
        this.groupUserDao = groupUserDao;
    }

    public boolean add(int gid, int uid) {
        GroupUserEntity entity = new GroupUserEntity();
        entity.setGid(gid);
        entity.setUid(uid);
        entity.setJoinTime(System.currentTimeMillis());
        return groupUserDao.add(sqlSession, entity);
    }

    public GroupUserEntity getMember(int gid, int uid) {
        // 根据Uid和Gid获取group
        GroupUserEntity entity = new GroupUserEntity();
        entity.setGid(gid);
        entity.setUid(uid);
        return groupUserDao.getMember(sqlSession, entity);
    }

    public List<Map<String, Object>> getMembers(int gid, PageRowBounds pager) {
        return groupUserDao.getMembers(sqlSession, gid, pager);
    }

    public boolean deleteMember(int gid, int uid) {
        //删除
        GroupUserEntity entity = new GroupUserEntity();
        entity.setGid(gid);
        entity.setUid(uid);
        return groupUserDao.deleteMember(sqlSession, entity);
    }
}
