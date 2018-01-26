package com.eagleoj.web.dao;

import com.eagleoj.web.entity.SettingEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface SettingMapper {
    List<SettingEntity> listAll();

    int updateByKey(String key);

    int batchSave(List<SettingEntity> list);
}
