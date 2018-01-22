package org.inlighting.oj.web.config;

import com.alibaba.fastjson.annotation.JSONField;
import org.inlighting.oj.judge.LanguageEnum;

import java.util.Map;

/**
 * @author Smith
 **/
public class SystemConfig {

    @JSONField(name = "is_installed")
    private boolean isInstalled;

    private String title;

    @JSONField(name = "judger_url")
    private String judgerUrl;

    private Map<LanguageEnum, String> lang;

    @JSONField(name = "oss_config")
    private OSSConfig ossConfig;

    public Map<LanguageEnum, String> getLang() {
        return lang;
    }

    public void setLang(Map<LanguageEnum, String> lang) {
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
