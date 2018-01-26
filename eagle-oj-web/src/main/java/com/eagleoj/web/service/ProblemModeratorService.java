package com.eagleoj.web.service;

import com.eagleoj.web.entity.ProblemModeratorEntity;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.ProblemModeratorDao;
import com.eagleoj.web.entity.ProblemModeratorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface ProblemModeratorService {

    boolean save(int pid, int uid);

    boolean isExist(int pid, int uid);

    ProblemModeratorEntity get(int pid, int uid);

    List<Map<String, Object>> listProblemModerators(int pid);

    boolean delete(int pid, int uid);
}
