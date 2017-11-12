package org.inlighting.oj.web.dao;


import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.AttachmentEntity;
import org.springframework.stereotype.Repository;

/**
 * @author = ygj
 **/
@Repository
public class AttachmentDao {

    public boolean addAttachment(SqlSession sqlSession,AttachmentEntity attachmentEntity){
        int insertNum = sqlSession.insert("attachment.addAttachment",attachmentEntity);
        return insertNum == 1;
    }


}
