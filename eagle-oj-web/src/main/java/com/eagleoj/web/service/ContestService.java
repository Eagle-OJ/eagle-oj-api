package com.eagleoj.web.service;

import com.eagleoj.web.entity.ContestEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ContestService {

    int save(String name, int owner, String slogan, String description,
                   long startTime, long endTime, Long totalTime, String password,
                   int type);

    List<ContestEntity> listByUid(int uid);

    ContestEntity getByCid(int cid);

    boolean deleteByCid(int cid);

    boolean updateDescriptionByCid (int cid, String name, String slogan, String description, long startTime,
                                           long endTime, Long totalTime, String password, int type);

    boolean updateStatusByCid(int cid, int status);

    List<Map<String, Object>> listValid();
}
