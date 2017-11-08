package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Smith
 **/
@Repository
public class TestCaseDao {

    public boolean add(SqlSession session, TestCaseEntity entity) {
        // todo
        return false;
    }
}
