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

    int count();

    List<UserEntity> listUserRank(@Param("admin") int admin, @Param("root") int root);

    UserEntity getByUid(int uid);

    UserEntity getByEmail(String email);

    UserEntity getByEmailPassword(@Param("email") String email, @Param("password") String password);

    List<UserEntity> listAll();

    int updateByUid(@Param("uid") int uid, @Param("data") UserEntity data);
}
