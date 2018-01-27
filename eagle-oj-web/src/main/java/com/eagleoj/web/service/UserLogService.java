package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.UserLogDao;
import com.eagleoj.web.entity.UserLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
public interface UserLogService {

    boolean save(int uid, UserLogEntity entity);

    List<UserLogEntity> listUserLogInWeek(int uid);

    List<UserLogEntity> listUserLogInMonth(int uid);
}
