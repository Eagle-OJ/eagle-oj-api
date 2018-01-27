package com.eagleoj.web.service;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.web.dao.SettingMapper;
import com.eagleoj.web.dao.UserMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.config.OSSConfig;
import com.eagleoj.web.config.SystemConfig;
import com.eagleoj.web.entity.SettingEntity;
import com.eagleoj.web.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface SettingService {

    SystemConfig getSystemConfig();

    List<SettingEntity> getAll();

    boolean installWeb(String email, String nickname, String password,
                       String title, String accessKey, String secretKey,
                       String endPoint, String bucket, String url);

    boolean updateSetting(String title, String accessKey, String secretKey,
                  String endPoint, String bucket, String url);

    void reloadSetting();


}
