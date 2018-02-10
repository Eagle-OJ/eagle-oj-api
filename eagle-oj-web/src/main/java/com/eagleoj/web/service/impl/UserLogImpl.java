package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.UserLogMapper;
import com.eagleoj.web.entity.UserLogEntity;
import com.eagleoj.web.service.UserLogService;
import com.eagleoj.web.util.WebUtil;
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
    public void save(int uid, UserLogEntity entity) {
        boolean flag = userLogMapper.saveByUid(uid, entity) > 0;
        WebUtil.assertIsSuccess(flag, "用户提交记录保存失败");
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
