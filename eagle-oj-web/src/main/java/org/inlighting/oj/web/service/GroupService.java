package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.GroupEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class GroupService {

    private final SqlSession sqlSession;

    public GroupService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int createGroup(int owner, int cover, String name, String password, long createTime) {
        // todo
        return 0;
    }

    public GroupEntity getByGid(int gid) {
        // todo
        return null;
    }
}
