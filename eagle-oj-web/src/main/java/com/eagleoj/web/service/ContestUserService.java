package com.eagleoj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.ContestUserDao;
import com.eagleoj.web.entity.ContestUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ContestUserService {
    boolean save(int cid, int uid);

    ContestUserEntity get(int cid, int uid);

    List<Map<String, Object>> listUserContests(int uid);

    List<Map<String, Object>> listNormalContestRank(int cid);

    List<Map<String, Object>> listACMContestRank(int cid, int penalty);

    boolean updateByCidUid(int cid, int uid, ContestUserEntity entity);
}
