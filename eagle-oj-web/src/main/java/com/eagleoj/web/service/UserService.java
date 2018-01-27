package com.eagleoj.web.service;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface UserService {

    int register(String email, String nickname, String password);

    UserEntity login(String email, String password);

    UserEntity getUserByUid(Integer uid);

    UserEntity getUserByEmail(String email);

    void updateUserProfile(Integer uid, String nickname, String motto, Integer gender);

    void updateUserTimes(Integer uid, UserEntity userEntity);

    void uploadUserAvatar(Integer uid, MultipartFile file);
}
