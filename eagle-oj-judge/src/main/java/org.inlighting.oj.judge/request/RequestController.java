package org.inlighting.oj.judge.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.inlighting.oj.judge.config.CodeLanguageEnum;
import org.inlighting.oj.judge.config.ProblemStatusEnum;
import org.inlighting.oj.judge.config.JudgerRequestBean;
import org.inlighting.oj.judge.config.JudgeResponseBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class RequestController {

    public static JudgeResponseBean judge(String judgerUrl, JudgerRequestBean request) {
        double[] realTime = new double[request.getTestCaseNumber()];
        double[] memory = new double[request.getTestCaseNumber()];
        String[] stderr = new String[request.getTestCaseNumber()];
        String[] output = new String[request.getTestCaseNumber()];
        ProblemStatusEnum[] statusEnums = new ProblemStatusEnum[request.getTestCaseNumber()];

        String[] tempResults = new String[request.getTestCaseNumber()];
        for (int i=0; i<request.getTestCaseNumber(); i++) {
            tempResults[i] = judgeEach(judgerUrl, request, i);
        }

        for (int i=0; i<request.getTestCaseNumber(); i++) {
            String tempResult = tempResults[i];
            // 默认情况
            realTime[i] = 0;
            memory[i] = 0;
            stderr[i] = "null";
            statusEnums[i] = ProblemStatusEnum.JudgeError;

            try {
                JSONObject obj = JSON.parseObject(tempResult);
                realTime[i] = obj.getDouble("time");
                memory[i] = obj.getDouble("memory")/1000;
                stderr[i] = obj.getString("stderr");
                output[i] = obj.getString("stdout");
                statusEnums[i] = convertStatus(obj.getJSONObject("status"));
            } catch (Exception e) {
                realTime[i] = 0;
                memory[i] = 0;
                stderr[i] = "null";
                output[i] = "";
                statusEnums[i] = ProblemStatusEnum.CompileError;
            }
        }
        JudgeResponseBean response = new JudgeResponseBean();
        response.setProblemStatusEnum(ProblemStatusEnum.Accepted);
        for (ProblemStatusEnum statusEnum: statusEnums) {
            if (statusEnum != ProblemStatusEnum.Accepted) {
                response.setProblemStatusEnum(statusEnum);
                break;
            }
        }


        response.setTestCaseNumber(request.getTestCaseNumber());
        response.setMemory(
                new BigDecimal(
                        Arrays.stream(memory).sum()/request.getTestCaseNumber()
                ).setScale(2, RoundingMode.HALF_UP).doubleValue()
        );
        response.setRealTime(
                new BigDecimal(
                        Arrays.stream(realTime).sum()/request.getTestCaseNumber()
                ).setScale(2, RoundingMode.HALF_UP).doubleValue()
        );
        response.setStderr(stderr);
        response.setOutput(output);
        response.setProblemStatusEnums(statusEnums);
        response.setDateline(System.currentTimeMillis());
        return response;
    }

    private static ProblemStatusEnum convertStatus(JSONObject status) {
        int id = status.getInteger("id");
        ProblemStatusEnum statusEnum = ProblemStatusEnum.JudgeError;
        if (id <= 2) {
            statusEnum = ProblemStatusEnum.JudgeError;
        } else if (id == 3) {
            statusEnum = ProblemStatusEnum.Accepted;
        } else if (id == 4) {
            statusEnum = ProblemStatusEnum.WrongAnswer;
        } else if (id == 5) {
            statusEnum = ProblemStatusEnum.TimeLimitExceeded;
        } else if (id == 6) {
            statusEnum = ProblemStatusEnum.CompileError;
        } else {
            statusEnum = ProblemStatusEnum.RuntimeError;
        }
        return statusEnum;
    }

    private static String judgeEach(String judgeUrl, JudgerRequestBean requestBean, int offset) {
        int languageId;
        switch (requestBean.getCodeLanguage()) {
            case JAVA8:
                languageId = 27;
                break;
            case CPP:
                languageId = 10;
                break;
            case C:
                languageId = 4;
                break;
            case PYTHON27:
                languageId = 36;
                break;
            case PYTHON36:
                languageId = 34;
                break;
            default:
                languageId = 34;
        }
        Judge0Api api = new Judge0Api(judgeUrl, requestBean.getSourceCode(), languageId, requestBean.getStdin()[offset],
                requestBean.getExpectResult()[offset], requestBean.getTimeLimit(), requestBean.getMemoryLimit());
        return api.send();
    }

    public static void main(String[] args) {
        JudgerRequestBean requestBean = new JudgerRequestBean();
        requestBean.setTestCaseNumber(2);
        requestBean.setMemoryLimit(128);
        requestBean.setTimeLimit(3);
        requestBean.setCodeLanguage(CodeLanguageEnum.PYTHON36);
        requestBean.setSourceCode("print(\"hello\")");
        requestBean.setStdin(new String[]{null, null});
        requestBean.setExpectResult(new String[]{null, null});
        System.out.println(JSON.toJSONString(judge("http://www.funnytu.com:3000/submissions?wait=true" ,requestBean)));
    }

}
