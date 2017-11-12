package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class AttachmentService {

    private final SqlSession sqlSession;

    public AttachmentService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int add(int owner, String url, long uploadTime) {
        // todo
        return 0;
    }


}
