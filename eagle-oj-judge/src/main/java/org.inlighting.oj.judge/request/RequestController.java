package org.inlighting.oj.judge.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.inlighting.oj.judge.bean.LanguageEnum;
import org.inlighting.oj.judge.bean.StdRequestBean;
import org.inlighting.oj.judge.bean.StdResponseBean;

import java.util.Arrays;

public class RequestController {

    public static StdResponseBean judge(StdRequestBean request) {
        double[] time = new double[request.getTestCaseNumber()];
        int[] memory = new int[request.getTestCaseNumber()];
        int[] status = new int[request.getTestCaseNumber()];
        String[] message = new String[request.getTestCaseNumber()];
        String[] stderr = new String[request.getTestCaseNumber()];
        String[] result = new String[request.getTestCaseNumber()];
        String[] tempResults = new String[request.getTestCaseNumber()];

        for (int i=0; i<request.getTestCaseNumber(); i++) {
            tempResults[i] = judgeEach(request, i);
        }

        for (int i=0; i<request.getTestCaseNumber(); i++) {
            String tempResult = tempResults[i];
            if (tempResult!=null) {
                JSONObject obj = JSON.parseObject(tempResult);
                time[i] = obj.getDouble("time");
                memory[i] = obj.getInteger("memory");
                message[i] = obj.getString("message");
                stderr[i] = obj.getString("stderr");
                result[i] = obj.getJSONObject("status").getString("description");
                status[i] = obj.getJSONObject("status").getInteger("id");
            } else {
                time[i] = 0;
                memory[i] = 0;
                message[i] = "null";
                stderr[i] = "null";
                result[i] = "Can't get judge result, please retry.";
                status[i] = 13;
            }
        }

        StdResponseBean response = new StdResponseBean();
        response.setAccept(true);
        for (int i: status) {
            if (i != 3) {
                response.setAccept(false);
                break;
            }
        }

        response.setTestCaseNumber(request.getTestCaseNumber());
        response.setMemory(Arrays.stream(memory).sum()/request.getTestCaseNumber());
        response.setRealTime(Arrays.stream(time).sum()/request.getTestCaseNumber());
        response.setMessage(message);
        response.setStderr(stderr);
        response.setResult(result);
        response.setDateline(System.currentTimeMillis());
        return response;
    }

    private static String judgeEach(StdRequestBean requestBean, int offset) {
        int languageId = 34;
        switch (requestBean.getLanguage()) {
            case C:
                languageId = 4;
                break;
            case CPP:
                languageId = 1;
                break;
            case JAVA8:
                languageId = 27;
                break;
            case PYTHON36:
                languageId = 34;
                break;
        }
        Judge0Api api = new Judge0Api(requestBean.getSourceCode(), languageId, requestBean.getStdin()[offset],
                requestBean.getExpectResult()[offset], requestBean.getTimeLimit(), requestBean.getMemoryLimit());
        return api.send();
    }

    public static void main(String[] args) {
        StdRequestBean requestBean = new StdRequestBean();
        requestBean.setTestCaseNumber(2);
        requestBean.setLanguage(LanguageEnum.PYTHON36);
        requestBean.setSourceCode("print(\"hello\")");
        requestBean.setStdin(new String[]{null, null});
        requestBean.setExpectResult(new String[]{null, null});
        System.out.println(JSON.toJSONString(judge(requestBean)));
    }

}
