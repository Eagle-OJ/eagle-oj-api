package com.eagleoj.web.controller.format.admin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateStorageSettingFormat {

    @NotNull
    private Boolean isOpenStorage;

    @Length(min = 1)
    private String accessKey;

    @Length(min = 1)
    private String secretKey;

    @Length(min = 1)
    private String endPoint;

    @Length(min = 1)
    private String bucket;

    @Length(min = 1)
    private String url;

    public Boolean getOpenStorage() {
        return isOpenStorage;
    }

    public void setOpenStorage(Boolean openStorage) {
        isOpenStorage = openStorage;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
