package com.eagleoj.web.service;

import com.eagleoj.web.dao.TestMapper;
import com.eagleoj.web.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TestService implements Test {
    @Autowired
    private TestMapper testMapper;

    @Transactional
    public List<UserEntity> getUser(int uid) {
        return testMapper.getUser(uid);
    }
}
