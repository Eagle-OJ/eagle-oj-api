package org.inlighting.oj.web.controller.format.index;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateSettingFormat {
    @NotNull
    @Length(max = 20)
    private String title;

    @NotNull
    private String accessKey;

    @NotNull
    private String secretKey;

    @NotNull
    private String endPoint;

    @NotNull
    private String bucket;

    @NotNull
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
