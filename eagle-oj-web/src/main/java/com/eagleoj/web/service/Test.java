package com.eagleoj.web.service;

import com.eagleoj.web.entity.UserEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface Test {
    List<UserEntity> getUser(int uid);
}
