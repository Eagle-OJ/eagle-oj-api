package com.eagleoj.judge.judger.eagle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.judge.entity.TestCaseResponseEntity;
import okhttp3.*;
import com.eagleoj.judge.entity.RequestEntity;
import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.judge.judger.JudgerApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Smith
 **/
public class Eagle implements JudgerApi {

    private final OkHttpClient CLIENT = new OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

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
        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        String json = response.body().string();
        return parseResponse(json);
    }

    private RequestBody formRequestBody() {
        String json = JSON.toJSONString(REQUEST_ENTITY, SerializerFeature.WriteMapNullValue);
        return RequestBody
                .create(MediaType.parse("application/json;charset=utf-8"), json);
    }

    private ResponseEntity parseResponse(String json) throws Exception {
        JSONObject obj = JSON.parseObject(json);
        ResultEnum result = convertStringToResult(obj.getString("result"));
        if (result.equals(ResultEnum.SE)) {
            throw new Exception("Remote judge error:"+obj.getString("error_message"));
        }

        JSONArray testCases = obj.getJSONArray("test_cases");
        List<TestCaseResponseEntity> testCaseList = new ArrayList<>(testCases.size());
        for (int i = 0; i<testCases.size(); i++) {
            JSONObject tempObj = testCases.getJSONObject(i);
            String tempErrorMessage = tempObj.getString("error_message");
            ResultEnum  tempResult = convertStringToResult(tempObj.getString("result"));
            TestCaseResponseEntity testCaseResponseEntity = new TestCaseResponseEntity(tempResult, tempErrorMessage);
            testCaseList.add(testCaseResponseEntity);
        }

        ResponseEntity responseEntity = new ResponseEntity(obj.getDouble("time"), obj.getInteger("memory"),
                result, testCaseList);
        return responseEntity;
    }

    private ResultEnum convertStringToResult(String result) {
        switch (result) {
            case "SE":
                return ResultEnum.SE;
            case "WA":
                return ResultEnum.WA;
            case "AC":
                return ResultEnum.AC;
            case "RTE":
                return ResultEnum.RTE;
            case "CE":
                return ResultEnum.CE;
            case "TLE":
                return ResultEnum.TLE;
            default:
                return ResultEnum.SE;
        }
    }

}
