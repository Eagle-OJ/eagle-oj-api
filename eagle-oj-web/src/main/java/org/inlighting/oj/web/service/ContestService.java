package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.inlighting.oj.web.entity.ContestEntity;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class ContestService {

    public boolean addContest(String name, int owner, JSONArray moderator, String slogan, String description,
                              long startTime, long endTime, long totalTime, String password, int official,
                              int type, long createTime) {
        // todo
        return false;
    }

    public ContestEntity getContestByCid(int cid) {
        // todo
        return null;
    }
}
