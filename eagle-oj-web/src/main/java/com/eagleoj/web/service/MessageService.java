package com.eagleoj.web.service;

import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.entity.MessageEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface MessageService {

    int save(int owner, int type, String content, JSONObject json);

    List<MessageEntity> listUserMessages(int owner);
}
