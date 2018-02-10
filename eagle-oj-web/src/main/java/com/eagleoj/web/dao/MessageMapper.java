package com.eagleoj.web.dao;

import com.eagleoj.web.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface MessageMapper {
    int save(MessageEntity messageEntity);

    List<MessageEntity> listMessagesByOwner(int owner);
}
