package com.eagleoj.web.dao;

import com.eagleoj.web.entity.AttachmentEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Smith
 **/
@Repository
public interface AttachmentMapper {
    int save(AttachmentEntity entity);

    AttachmentEntity getByAid(int aid);
}
