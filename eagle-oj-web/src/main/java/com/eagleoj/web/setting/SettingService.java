package com.eagleoj.web.setting;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.SettingMapper;
import com.eagleoj.web.dao.UserMapper;
import com.eagleoj.web.entity.SettingEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.util.WebUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class SettingService {

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private UserMapper userMapper;

    private Map<String, String> settingMap;

    public synchronized String getSetting(String key) {
        if (settingMap == null) {
            refresh();
        }
        return settingMap.get(key);
    }

    public synchronized void refresh() {
        settingMap = new HashMap<>();
        List<SettingEntity> list = settingMapper.listAll();
        for (SettingEntity entity: list) {
            settingMap.put(entity.getKey(), entity.getValue());
        }
    }

    public void updateSetting(String key, String value) {
        boolean flag = settingMapper.updateByKey(key, value) == 1;
        WebUtil.assertIsSuccess(flag, "网站设置更新失败");
    }

    public boolean isInstalled() {
        if (settingMap == null) {
            refresh();
        }

        if (settingMap == null) {
            return false;
        }
        return settingMap.size() > 0;
    }

    public Map<LanguageEnum, String> getLangMap() {
        Map<LanguageEnum, String> lang = new HashMap<>();
        for (LanguageEnum languageEnum: LanguageEnum.values()) {
            lang.put(languageEnum, languageEnum.getName());
        }
        return lang;
    }

    @Transactional
    public void installWeb(String email, String nickname, String password,
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
        boolean flag = userMapper.saveRoot(userEntity) == 1;
        WebUtil.assertIsSuccess(flag, "管理员注册失败");

        List<SettingEntity> list = new ArrayList<>();
        SettingEntity settingEntity1 = new SettingEntity(SettingKeyMapper.WEB_TITLE, title);
        SettingEntity settingEntity2 = new SettingEntity(SettingKeyMapper.OSS_ACCESS_KEY, accessKey);
        SettingEntity settingEntity3 = new SettingEntity(SettingKeyMapper.OSS_SECRET_KEY, secretKey);
        SettingEntity settingEntity4 = new SettingEntity(SettingKeyMapper.OSS_END_POINT, endPoint);
        SettingEntity settingEntity5 = new SettingEntity(SettingKeyMapper.OSS_BUCKET, bucket);
        SettingEntity settingEntity6 = new SettingEntity(SettingKeyMapper.OSS_URL, url);
        list.add(settingEntity1);
        list.add(settingEntity2);
        list.add(settingEntity3);
        list.add(settingEntity4);
        list.add(settingEntity5);
        list.add(settingEntity6);
        flag = settingMapper.batchSave(list) == 6;
        WebUtil.assertIsSuccess(flag, "网站安装失败");
    }
}
