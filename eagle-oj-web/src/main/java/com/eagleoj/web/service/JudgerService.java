package com.eagleoj.web.service;

import com.eagleoj.web.entity.JudgerEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface JudgerService {
    List<JudgerEntity> listJudger();

    void addJudger(String url, int port);

    void deleteJudger(int jid);

    void updateJudger(int jid, String url, Integer port);
}
