package com.eagleoj.web.dao;

import org.apache.ibatis.annotations.Mapper;
import com.eagleoj.web.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface TestMapper {
    List<UserEntity> getUser(int uid);
}
