package com.eagleoj.judge.judger.eagle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import okhttp3.*;
import com.eagleoj.judge.entity.RequestEntity;
import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.judge.judger.JudgerApi;

import java.io.IOException;

/**
 * @author Smith
 **/
public class Eagle implements JudgerApi {

    private final OkHttpClient CLIENT = new OkHttpClient();

    private String REQUEST_URL;

    private RequestEntity REQUEST_ENTITY;

    @Override
    public ResponseEntity judge(String url, RequestEntity requestEntity) throws Exception {
        REQUEST_URL = url+"/judge";
        this.REQUEST_ENTITY = requestEntity;
        return send();
    }

    private ResponseEntity send() throws Exception {
        Request request = new Request.Builder()
                .url(REQUEST_URL)
                .header("Content-Type", "application/json")
                .post(formRequestBody())
                .build();
        String json;
        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            json = response.body().string();
        }
        System.out.println(json);
        return null;
    }

    private RequestBody formRequestBody() {
        String json = JSON.toJSONString(REQUEST_ENTITY, SerializerFeature.WriteMapNullValue);
        System.out.println("send data:"+json);
        return RequestBody
                .create(MediaType.parse("application/json;charset=utf-8"), json);
    }

}