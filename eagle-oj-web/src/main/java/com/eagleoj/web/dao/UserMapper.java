package com.eagleoj.web.dao;

import com.eagleoj.web.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface UserMapper {

    int save(UserEntity userEntity);

    int saveRoot(UserEntity userEntity);

    List<UserEntity> listUserRank();

    UserEntity getByUid(int uid);

    UserEntity getByEmail(String email);

    UserEntity getByEmailPassword(@Param("email") String email, @Param("password") String password);

    List<UserEntity> listInUidList(List<Integer> uidList);

    // todo
    List<Map<String, Object>> listModeratorsInUidList(List<Integer> uidList);

    int updateTimesByUid(@Param("uid") int uid, @Param("data") UserEntity data);

    int updateProfileByUid(@Param("uid") int uid, @Param("data") UserEntity data);

    int updateAvatarByUid(@Param("uid") int uid, @Param("avatar") int avatar);
}
