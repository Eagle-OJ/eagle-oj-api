package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.UserLogMapper;
import com.eagleoj.web.entity.UserLogEntity;
import com.eagleoj.web.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class UserLogImpl implements UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public boolean save(int uid, UserLogEntity entity) {
        return userLogMapper.saveByUid(uid, entity) == 1;
    }

    @Override
    public List<UserLogEntity> listUserLogInWeek(int uid) {
        return userLogMapper.listInWeekByUid(uid);
    }

    @Override
    public List<UserLogEntity> listUserLogInMonth(int uid) {
        return userLogMapper.listInMonthByUid(uid);
    }
}
