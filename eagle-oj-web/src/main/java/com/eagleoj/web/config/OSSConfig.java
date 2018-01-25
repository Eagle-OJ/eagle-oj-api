package com.eagleoj.web.config;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class OSSConfig {
    @JSONField(name = "end_point")
    private String END_POINT;

    @JSONField(name = "url")
    private String URL;

    @JSONField(name = "access_key")
    private String ACCESS_KEY;

    @JSONField(name = "secret_key")
    private String SECRET_KEY;

    @JSONField(name = "bucket")
    private String BUCKET;

    public String getEND_POINT() {
        return END_POINT;
    }

    public void setEND_POINT(String END_POINT) {
        this.END_POINT = END_POINT;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getACCESS_KEY() {
        return ACCESS_KEY;
    }

    public void setACCESS_KEY(String ACCESS_KEY) {
        this.ACCESS_KEY = ACCESS_KEY;
    }

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public String getBUCKET() {
        return BUCKET;
    }

    public void setBUCKET(String BUCKET) {
        this.BUCKET = BUCKET;
    }
}
