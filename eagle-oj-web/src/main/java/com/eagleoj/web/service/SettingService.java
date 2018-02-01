package com.eagleoj.web.service;


import com.eagleoj.web.config.SystemConfig;
import com.eagleoj.web.entity.SettingEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface SettingService {

    SystemConfig getSystemConfig();

    List<SettingEntity> getAll();

    void installWeb(String email, String nickname, String password,
                       String title, String accessKey, String secretKey,
                       String endPoint, String bucket, String url, String judgerUrl);

    void updateSetting(String title, String accessKey, String secretKey,
                  String endPoint, String bucket, String url, String judgerUrl);

    void reloadSetting();


}
