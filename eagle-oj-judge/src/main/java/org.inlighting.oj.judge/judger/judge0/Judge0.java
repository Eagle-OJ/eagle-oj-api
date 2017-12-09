package org.inlighting.oj.judge.judger.judge0;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.ResultEnum;
import org.inlighting.oj.judge.entity.RequestEntity;
import org.inlighting.oj.judge.entity.ResponseEntity;
import org.inlighting.oj.judge.entity.TestCaseRequestEntity;
import org.inlighting.oj.judge.entity.TestCaseResponseEntity;
import org.inlighting.oj.judge.judger.JudgerApi;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
public class Judge0 implements JudgerApi {

    private final OkHttpClient client = new OkHttpClient();

    private String REQUEST_URL = null;

    private RequestEntity REQUEST_ENTITY;

    @Override
    public ResponseEntity judge(String url, RequestEntity requestEntity) throws Exception {
        this.REQUEST_URL = url+"/submissions?wait=true&fields=time,memory,stderr,compile_output,status";
        this.REQUEST_ENTITY = requestEntity;
        List<ResponseEntity> tempResponses = new ArrayList<>(requestEntity.getTestCases().size());
        for (int i=0; i<requestEntity.getTestCases().size(); i++) {
            ResponseEntity responseEntity = judgeEach(i);
            tempResponses.add(responseEntity);
        }
        return getResponseEntity(tempResponses);
    }

    private ResponseEntity getResponseEntity(List<ResponseEntity> tempResponses) {
        int length = tempResponses.size();
        int countLength = 0;
        double totalTime = 0;
        int totalMemory = 0;
        ResultEnum result = ResultEnum.AC;
        List<TestCaseResponseEntity> testCases = new ArrayList<>(length);
        for (ResponseEntity responseEntity: tempResponses) {
            if (result == ResultEnum.AC) {
                if (responseEntity.getResult() != ResultEnum.AC) {
                    result = responseEntity.getResult();
                }
            }
            TestCaseResponseEntity testCase = responseEntity.getTestCases().get(0);
            testCases.add(testCase);
            if (responseEntity.getMemory()!=0 && responseEntity.getTime()!=0) {
                totalMemory = totalMemory + responseEntity.getMemory();
                totalTime = totalTime + responseEntity.getTime();
                countLength++;
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        double time = Double.valueOf(df.format(totalTime / countLength));
        return new ResponseEntity(time, (int) Math.ceil(totalMemory/(double)countLength), result, testCases);
    }

    private ResponseEntity judgeEach(int index) throws Exception {
        TestCaseRequestEntity testCase = REQUEST_ENTITY.getTestCases().get(index);
        RequestBody formBody = new FormBody.Builder()
                .add("source_code", REQUEST_ENTITY.getSourceCode())
                .add("language_id", String.valueOf(getLanguageId(REQUEST_ENTITY.getLang())))
                .add("stdin", String.valueOf(testCase.getStdin()))
                .add("expected_output", String.valueOf(testCase.getStdout()))
                .add("cpu_time_limit", String.valueOf(REQUEST_ENTITY.getTimeLimit()))
                .add("memory_limit", String.valueOf(REQUEST_ENTITY.getMemoryLimit() * 1000))
                .build();
        Request request = new Request.Builder()
                .url(REQUEST_URL)
                .post(formBody)
                .build();
        String json = null;
        try (Response response = client.newCall(request).execute()) {
            if (! response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            json = response.body().string();
        }
        return parse(json);
    }

    private ResponseEntity parse(String json) {
        // System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);
        if (json == null) {
            List<TestCaseResponseEntity> testCases = new ArrayList<>(1);
            TestCaseResponseEntity testCase = new TestCaseResponseEntity(ResultEnum.SE, ResultEnum.SE.getName());
            testCases.add(testCase);
            return new ResponseEntity(0, 0, ResultEnum.SE, testCases);
        }
        JSONObject status = jsonObject.getJSONObject("status");
        ResultEnum result = getResult(status.getInteger("id"));
        DecimalFormat df = new DecimalFormat("#.##");
        double time = Double.valueOf(df.format(jsonObject.getDouble("time")));
        int memory = (int) Math.ceil(jsonObject.getDouble("memory")/1000);

        if (result == ResultEnum.CE) {
            // ce
            String errorMessage = jsonObject.getString("compile_output");
            List<TestCaseResponseEntity> testCases = new ArrayList<>(1);
            testCases.add(new TestCaseResponseEntity(ResultEnum.CE, errorMessage));
            return new ResponseEntity(0, 0, ResultEnum.CE, testCases);
        } else if (result == ResultEnum.RTE){
            // rte
            String errorMessage = jsonObject.getString("stderr");
            List<TestCaseResponseEntity> testCases = new ArrayList<>(1);
            testCases.add(new TestCaseResponseEntity(ResultEnum.RTE, errorMessage));
            return new ResponseEntity(time, memory, ResultEnum.RTE, testCases);
        } else if (result == ResultEnum.WA) {
            // wa
            List<TestCaseResponseEntity> testCases = new ArrayList<>(1);
            testCases.add(new TestCaseResponseEntity(ResultEnum.WA, ResultEnum.WA.getName()));
            return new ResponseEntity(time, memory, ResultEnum.WA, testCases);
        } else if (result == ResultEnum.TLE) {
            // tle
            List<TestCaseResponseEntity> testCases = new ArrayList<>(1);
            testCases.add(new TestCaseResponseEntity(ResultEnum.TLE, ResultEnum.TLE.getName()));
            return new ResponseEntity(REQUEST_ENTITY.getTimeLimit(), memory, ResultEnum.TLE, testCases);
        } else if (result == ResultEnum.AC) {
            // ac
            List<TestCaseResponseEntity> testCases = new ArrayList<>(1);
            testCases.add(new TestCaseResponseEntity(ResultEnum.AC, "null"));
            return new ResponseEntity(time, memory, ResultEnum.AC, testCases);
        } else {
            // se
            List<TestCaseResponseEntity> testCases = new ArrayList<>(1);
            testCases.add(new TestCaseResponseEntity(ResultEnum.SE, ResultEnum.SE.getName()));
            return new ResponseEntity(0, 0, ResultEnum.SE, testCases);
        }
    }

    private int getLanguageId(LanguageEnum lang) {
        switch (lang) {
            case JAVA8:
                return 27;
            case PYTHON36:
                return 34;
            case PYTHON27:
                return 36;
            case C:
                return 9;
            case CPP:
                return 15;
            default:
                return 34;
        }
    }

    private ResultEnum getResult(int status) {
        if (status == 3) {
            return ResultEnum.AC;
        } else if (status == 4) {
            return ResultEnum.WA;
        } else if (status == 5) {
            return ResultEnum.TLE;
        } else if (status == 6) {
            return ResultEnum.CE;
        } else if (status >=7 && status<=12) {
            return ResultEnum.RTE;
        } else {
            return ResultEnum.SE;
        }
    }
}
