package com.eagleoj.web.setting;

/**
 * @author Smith
 **/
public enum SettingEnum {
    IS_INSTALLED("is_installed"),

    WEB_TITLE("web_title"),

    LANG("lang"),

    IS_OPEN_STORAGE("is_open_storage"), OSS_END_POINT("oss_end_point"), OSS_URL("oss_url"),
    OSS_ACCESS_KEY("oss_access_key"), OSS_SECRET_KEY("oss_secret_key"), OSS_BUCKET("oss_bucket"),

    IS_OPEN_MAIL("is_open_mail"),
    MAIL_HOST("mail_host"), MAIL_PORT("mail_port"), MAIL_USERNAME("mail_username"), MAIL_PASSWORD("mail_password");

    private String name;

    SettingEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
