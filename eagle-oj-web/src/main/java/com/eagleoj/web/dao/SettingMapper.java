package com.eagleoj.web.dao;

import com.eagleoj.web.entity.SettingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface SettingMapper {
    List<SettingEntity> listAll();

    int updateByKey(@Param("key") String key, @Param("value") String value);

    int batchSave(List<SettingEntity> list);
}
