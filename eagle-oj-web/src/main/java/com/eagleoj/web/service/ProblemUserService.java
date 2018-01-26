package com.eagleoj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ProblemUserDao;
import com.eagleoj.web.entity.ProblemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ProblemUserService {

    boolean save(int pid, int uid, ResultEnum status);

    ProblemUserEntity get(int pid, int uid);

    List<Map<String, Object>> listUserProblemHistory(int uid);

    boolean updateByPid(int pid, int uid, ResultEnum result);
}
