package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.AttachmentDao;
import org.inlighting.oj.web.entity.AttachmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class AttachmentService {

    private final SqlSession sqlSession;

    private AttachmentDao attachmentDao;

    public AttachmentService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    @Autowired
    public void setAttachmentDao(AttachmentDao attachmentDao) {
        this.attachmentDao = attachmentDao;
    }

    public int add(int owner, String url, long uploadTime) {
        // 添加attachment
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setOwner(owner);
        attachmentEntity.setUrl(url);
        attachmentEntity.setUploadTime(uploadTime);
        boolean flag = attachmentDao.addAttachment(sqlSession,attachmentEntity);

        return flag ? attachmentEntity.getAid():0;
    }

    public AttachmentEntity get(int aid) {
        return attachmentDao.getAttachment(sqlSession, aid);
    }
}
