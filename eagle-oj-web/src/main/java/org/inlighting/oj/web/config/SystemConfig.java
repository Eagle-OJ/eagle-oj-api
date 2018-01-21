package org.inlighting.oj.web.config;

import com.alibaba.fastjson.annotation.JSONField;
import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.web.entity.SettingEntity;
import org.inlighting.oj.web.service.SettingService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Component
public class SystemConfig {

    private SettingService settingService;

    public SystemConfig(SettingService service) {
        this.settingService = service;
        init();
    }

    private void init() {
        List<SettingEntity> list = settingService.getAll();
        if (list.size() == 0) {
            isInstalled = false;
            return;
        }

        for (SettingEntity entity: list) {
            autoSetting(entity);
        }

        // 添加编程语言配置文件
        {
            for (LanguageEnum languageEnum: LanguageEnum.values()) {
                lang.put(languageEnum, languageEnum.getName());
            }
        }


    }

    private void autoSetting(SettingEntity entity) {
        String key = entity.getKey();
        if (key.equals("title")) {
            title = entity.getValue();
        }
    }

    @JSONField(name = "is_installed")
    private boolean isInstalled;

    private String title;

    @JSONField(serialize = false)
    private String judgerUrl;

    private Map<Object, String> lang;

    @JSONField(name = "oss_config")
    private OSSConfig ossConfig;

    public Map<Object, String> getLang() {
        return lang;
    }

    public void setLang(Map<Object, String> lang) {
        this.lang = lang;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setInstalled(boolean installed) {
        isInstalled = installed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJudgerUrl() {
        return judgerUrl;
    }

    public void setJudgerUrl(String judgerUrl) {
        this.judgerUrl = judgerUrl;
    }

    public OSSConfig getOssConfig() {
        return ossConfig;
    }

    public void setOssConfig(OSSConfig ossConfig) {
        this.ossConfig = ossConfig;
    }
}
