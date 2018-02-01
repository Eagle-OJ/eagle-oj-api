package com.eagleoj.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.config.OSSConfig;
import com.eagleoj.web.config.SystemConfig;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.SettingMapper;
import com.eagleoj.web.dao.UserMapper;
import com.eagleoj.web.entity.SettingEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.SettingService;
import com.eagleoj.web.util.WebUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
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

    @Transactional
    @Override
    public void installWeb(String email, String nickname, String password,
                              String title, String accessKey, String secretKey,
                              String endPoint, String bucket, String url, String judgerUrl) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setUid(1);
        userEntity.setRole(9);
        userEntity.setRegisterTime(System.currentTimeMillis());
        boolean res1 = userMapper.saveRoot(userEntity) == 1;
        if (! res1) {
            throw new WebErrorException("管理员注册失败");
        }
        List<SettingEntity> list = new ArrayList<>();
        SettingEntity setting1 = new SettingEntity("title", title);
        SettingEntity setting2 = new SettingEntity("oss_access_key", accessKey);
        SettingEntity setting3 = new SettingEntity("oss_secret_key", secretKey);
        SettingEntity setting4 = new SettingEntity("oss_end_point", endPoint);
        SettingEntity setting5 = new SettingEntity("oss_bucket", bucket);
        SettingEntity setting6 = new SettingEntity("oss_url", url);
        SettingEntity setting7 = new SettingEntity("judger_url", judgerUrl);
        list.add(setting1);
        list.add(setting2);
        list.add(setting3);
        list.add(setting4);
        list.add(setting5);
        list.add(setting6);
        list.add(setting7);
        boolean flag = settingMapper.batchSave(list) > 0;
        WebUtil.assertIsSuccess(flag, "网站安装失败");
    }

    @Transactional
    @Override
    public void updateSetting(String title, String accessKey, String secretKey,
                                 String endPoint, String bucket, String url, String judgerUrl) {
        // todo 待优化
        boolean res1 = settingMapper.updateByKey("title" , title) == 1;
        boolean res2 = settingMapper.updateByKey("oss_access_key", accessKey) == 1;
        boolean res3 = settingMapper.updateByKey("oss_secret_key", secretKey) == 1;
        boolean res4 = settingMapper.updateByKey("oss_end_point", endPoint) == 1;
        boolean res5 = settingMapper.updateByKey("oss_bucket", bucket) == 1;
        boolean res6 = settingMapper.updateByKey("oss_url", url) == 1;
        boolean res7 = settingMapper.updateByKey("judger_url", judgerUrl) == 1;
        WebUtil.assertIsSuccess(res1 && res2 && res3 && res4 && res5 && res6 && res7, "网站配置更新失败");
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
            case "judger_url":
                systemConfig.setJudgerUrl(entity.getValue());
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
