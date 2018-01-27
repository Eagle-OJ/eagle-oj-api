package com.eagleoj.web.dao;

import com.eagleoj.web.entity.UserLogEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface UserLogMapper {
    int saveByUid(@Param("uid") int uid, @Param("data") UserLogEntity userLogEntity);

    List<UserLogEntity> listInWeekByUid(int uid);

    List<UserLogEntity> listInMonthByUid(int uid);
}
