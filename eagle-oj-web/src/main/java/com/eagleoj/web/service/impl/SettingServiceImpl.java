package com.eagleoj.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.config.OSSConfig;
import com.eagleoj.web.config.SystemConfig;
import com.eagleoj.web.dao.SettingMapper;
import com.eagleoj.web.dao.UserMapper;
import com.eagleoj.web.entity.SettingEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.SettingService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class SettingServiceImpl implements SettingService {

    private SettingMapper settingMapper;

    private UserMapper userMapper;

    private SystemConfig systemConfig;

    public SettingServiceImpl(SettingMapper settingMapper, UserMapper userMapper) {
        this.settingMapper = settingMapper;
        this.userMapper = userMapper;
        init();
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public List<SettingEntity> getAll() {
        return settingMapper.listAll();
    }

    @Override
    public boolean installWeb(String email, String nickname, String password,
                              String title, String accessKey, String secretKey,
                              String endPoint, String bucket, String url) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setUid(1);
        userEntity.setRole(9);
        userEntity.setRegisterTime(System.currentTimeMillis());
        boolean res1 = userMapper.save(userEntity) == 1;
        if (! res1) {
            return false;
        }
        List<SettingEntity> list = new ArrayList<>();
        SettingEntity setting1 = new SettingEntity("title", title);
        SettingEntity setting2 = new SettingEntity("oss_access_key", accessKey);
        SettingEntity setting3 = new SettingEntity("oss_secret_key", secretKey);
        SettingEntity setting4 = new SettingEntity("oss_end_point", endPoint);
        SettingEntity setting5 = new SettingEntity("oss_bucket", bucket);
        SettingEntity setting6 = new SettingEntity("oss_url", url);
        list.add(setting1);
        list.add(setting2);
        list.add(setting3);
        list.add(setting4);
        list.add(setting5);
        list.add(setting6);
        return settingMapper.batchSave(list) > 0;
    }

    @Override
    public boolean updateSetting(String title, String accessKey, String secretKey,
                                 String endPoint, String bucket, String url) {
        // todo 待优化
        boolean res1 = settingMapper.updateByKey("title" , title) == 1;
        boolean res2 = settingMapper.updateByKey("oss_access_key", accessKey) == 1;
        boolean res3 = settingMapper.updateByKey("oss_secret_key", secretKey) == 1;
        boolean res4 = settingMapper.updateByKey("oss_end_point", endPoint) == 1;
        boolean res5 = settingMapper.updateByKey("oss_bucket", bucket) == 1;
        boolean res6 = settingMapper.updateByKey("oss_url", url) == 1;
        return res1 && res2 && res3 && res4 && res5 && res6;
    }

    @Override
    public void reloadSetting() {
        init();
    }

    private void init() {
        systemConfig = new SystemConfig();
        systemConfig.setOssConfig(new OSSConfig());
        List<SettingEntity> list = getAll();
        if (list.size() == 0) {
            systemConfig.setInstalled(false);
            return;
        } else {
            systemConfig.setInstalled(true);
        }

        for (SettingEntity entity: list) {
            autoSetting(entity);
        }

        // 添加编程语言配置文件
        {
            Map<LanguageEnum, String> lang = new HashMap<>();
            for (LanguageEnum languageEnum: LanguageEnum.values()) {
                lang.put(languageEnum, languageEnum.getName());
            }
            systemConfig.setLang(lang);
        }


    }

    private void autoSetting(SettingEntity entity) {
        String key = entity.getKey();
        OSSConfig ossConfig = systemConfig.getOssConfig();
        switch (key) {
            case "title":
                systemConfig.setTitle(entity.getValue());
                break;
            case "oss_access_key":
                ossConfig.setACCESS_KEY(entity.getValue());
                break;
            case "oss_secret_key":
                ossConfig.setSECRET_KEY(entity.getValue());
                break;
            case "oss_bucket":
                ossConfig.setBUCKET(entity.getValue());
                break;
            case "oss_end_point":
                ossConfig.setEND_POINT(entity.getValue());
                break;
            case "oss_url":
                ossConfig.setURL(entity.getValue());
                break;
        }
    }
}
