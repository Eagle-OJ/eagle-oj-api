package com.eagleoj.web.service;

import com.eagleoj.web.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface UserService {

    int save(String email, String nickname, String password);

    UserEntity getUserByEmailPassword(String email, String password);

    UserEntity getUserByUid(int uid);

    UserEntity getUserByEmail(String email);

    List<Map<String, Object>> listModeratorsInUidList(List<Integer> uidList);

    boolean updateUserProfile(int uid, String nickname, String motto, int gender);

    boolean updateUserAvatar(int uid, int avatar);

    boolean updateUserTimes(int uid, UserEntity userEntity);
}
