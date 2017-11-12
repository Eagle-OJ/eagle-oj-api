package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.GroupUserInfoEntity;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class GroupUserInfoService {

    private SqlSession sqlSession;

    public GroupUserInfoService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public boolean add(int gid, int uid, long joinTime) {
        // todo
        return false;
    }

    public GroupUserInfoEntity getByGidAndUid(int gid, int uid) {
        // todo
        return null;
    }

    public boolean deleteByGidAndUid(int gid, int uid) {
        // todo
        return false;
    }
}
