package org.inlighting.oj.judge.request;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

@Deprecated
class HackApi extends RequestBody {

    private final String API_URL = "http://api.hackerrank.com/checker/submission.json";

    HackApi(String sourceCode, int languageId, String[] inputs) {
        SOURCE_CODE = sourceCode;
        LANGUAGE_ID = languageId;
        INPUTS = inputs;
    }


    @Override
    public String send() {
        Map<String, String> param = new HashMap<>();
        param.put("source", SOURCE_CODE);
        param.put("lang", JSON.toJSONString(LANGUAGE_ID));
        param.put("testcases", JSON.toJSONString(INPUTS));
        param.put("api_key", "hackerrank|3227226-2046|8ad484a69591707d16efa5e3fbde4e5185aa2479");
        param.put("wait", JSON.toJSONString(WAIT));
        param.put("format", "json");
        return HttpUtil.post(API_URL, convertParam(param));
    }

}
