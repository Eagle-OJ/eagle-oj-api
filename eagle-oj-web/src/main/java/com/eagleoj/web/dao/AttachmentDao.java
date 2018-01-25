package com.eagleoj.web.dao;


import com.eagleoj.web.entity.AttachmentEntity;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.AttachmentEntity;
import org.springframework.stereotype.Repository;

/**
 * @author = ygj
 **/
@Repository
public class AttachmentDao {

    public boolean addAttachment(SqlSession sqlSession,AttachmentEntity attachmentEntity) {
        int insertNum = sqlSession.insert("attachment.addAttachment",attachmentEntity);
        return insertNum == 1;
    }

    public AttachmentEntity getAttachment(SqlSession sqlSession, int aid) {
        return sqlSession.selectOne("attachment.getAttachment", aid);
    }
}
