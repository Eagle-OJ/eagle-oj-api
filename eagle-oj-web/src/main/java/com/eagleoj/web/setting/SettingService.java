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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Smith
 **/
@Service
public class SettingService {

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private UserMapper userMapper;

    private ConcurrentHashMap<String, String> settingMap;

    public SettingService() {
        this.settingMap = new ConcurrentHashMap<>();
    }

    public void saveSetting(SettingEnum settingEnum, String value) {
        SettingEntity settingEntity = new SettingEntity(settingEnum.getName(), value);
        boolean flag = settingMapper.save(settingEntity) == 1;
        WebUtil.assertIsSuccess(flag, "保存设置失败");
        refresh(settingEnum);
    }

    public void saveSettings(List<SettingEnum> settingEnums, List<String> values) {
        int size = settingEnums.size();
        List<SettingEntity> list = new ArrayList<>(size);
        for (int i=0; i<size; i++) {
            list.add(new SettingEntity(settingEnums.get(i).getName(), values.get(i)));
        }
        boolean flag = settingMapper.saveList(list) > 0;
        WebUtil.assertIsSuccess(flag, "批量保存设置失败");
        refreshList(settingEnums);
    }

    public String getSetting(SettingEnum settingEnum) {
        if (settingMap.contains(settingEnum.getName())) {
            return settingMap.get(settingEnum.getName());
        } else {
            SettingEntity settingEntity = settingMapper.getByKey(settingEnum.getName());
            WebUtil.assertNotNull(settingEntity, "不存在此设置");
            settingMap.put(settingEntity.getKey(), settingEntity.getValue());
            return settingEntity.getValue();
        }
    }

    public List<String> listSettings(List<SettingEnum> settingEnums) {
        int size = settingEnums.size();
        List<String> resultList = new ArrayList<>(size);
        List<String> keyList = new ArrayList<>(size);
        for (SettingEnum settingEnum: settingEnums) {
            if (settingMap.contains(settingEnum.getName())) {
                resultList.add(settingMap.get(settingEnum.getName()));
            } else {
                keyList.add(settingEnum.getName());
            }
        }
        List<SettingEntity> list = settingMapper.listByKeys(keyList);
        for (SettingEntity entity: list) {
            resultList.add(entity.getValue());
            settingMap.put(entity.getKey(), entity.getValue());
        }
        return resultList;
    }

    public void updateSetting(SettingEnum settingEnum, String value) {
        SettingEntity settingEntity = new SettingEntity(settingEnum.getName(), value);
        boolean flag = settingMapper.save(settingEntity) > 0;
        WebUtil.assertIsSuccess(flag, "更新设置失败");
        refresh(settingEnum);
    }

    public void updateSettings(List<SettingEnum> settingEnums, List<String> values) {
        int size = settingEnums.size();
        List<SettingEntity> list = new ArrayList<>(size);
        for (int i=0; i<settingEnums.size(); i++) {
            list.add(new SettingEntity(settingEnums.get(i).getName(), values.get(i)));
        }
        boolean flag = settingMapper.saveList(list) > 0;
        WebUtil.assertIsSuccess(flag, "批量更新设置失败");
        refreshList(settingEnums);
    }

    public boolean isInstalled() {
        try {
            String s = getSetting(SettingEnum.IS_INSTALLED);
            return Boolean.valueOf(s);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOpenStorage() {
        try {
            String s = getSetting(SettingEnum.IS_OPEN_STORAGE);
            return Boolean.valueOf(s);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOpenMail() {
        try {
            String s= getSetting(SettingEnum.IS_OPEN_MAIL);
            return Boolean.valueOf(s);
        } catch (Exception e) {
            return false;
        }
    }

    private void refresh(SettingEnum settingEnum) {
        if (settingMap.contains(settingEnum.getName())) {
            settingMap.remove(settingEnum.getName());
            getSetting(settingEnum);
        }
    }

    private void refreshList(List<SettingEnum> settingEnums) {
        for (SettingEnum settingEnum: settingEnums) {
            if (settingMap.contains(settingEnum.getName())) {
                settingMap.remove(settingEnum.getName());
            }
        }
        listSettings(settingEnums);
    }

    @Transactional
    public void installWeb(String email, String nickname, String password, String title) {
        if (isInstalled()) {
            throw new WebErrorException("网站已经安装过了");
        }

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

        List<SettingEnum> keys = new ArrayList<>(2);
        List<String> values = new ArrayList<>(2);
        keys.add(SettingEnum.IS_INSTALLED);
        values.add("true");
        keys.add(SettingEnum.WEB_TITLE);
        values.add(title);
        saveSettings(keys, values);
    }
}
