package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.ProblemInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Smith
 **/
@Repository
public class ProblemInfoDao {

    public boolean add(SqlSession session, ProblemInfoEntity entity) {
        return session.insert("problemInfo.insertProblemInfo", entity) == 1;
    }

    public ProblemEntity get(int pid) {
        // todo
        return null;
    }
}
