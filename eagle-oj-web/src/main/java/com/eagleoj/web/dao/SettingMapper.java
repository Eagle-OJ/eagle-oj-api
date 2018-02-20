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

    int save(SettingEntity entity);

    int saveList(List<SettingEntity> entities);

    SettingEntity getByKey(String key);

    List<SettingEntity> listByKeys(List<String> keys);
}
