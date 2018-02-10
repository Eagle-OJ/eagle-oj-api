package com.eagleoj.web.service;

import com.eagleoj.web.entity.UserLogEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface UserLogService {

    void save(int uid, UserLogEntity entity);

    List<UserLogEntity> listUserLogInWeek(int uid);

    List<UserLogEntity> listUserLogInMonth(int uid);
}
