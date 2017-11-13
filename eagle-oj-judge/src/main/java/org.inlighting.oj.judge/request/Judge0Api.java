package org.inlighting.oj.judge.request;

import java.util.HashMap;
import java.util.Map;

class Judge0Api implements RequestBase {

    //private final String API_URL = GlobalConfigHelper.JUDGE_API_URL;
    // private final String API_URL = "http://localhost:3000/submissions?wait=true";
    private final String API_URL;

    private final String SOURCE_CODE;

    private final int LANGUAGE_ID;

    private final String STDIN;

    private final String EXPECT_OUTPUT;

    // second
    private final int TIME_LIMIT;

    // mb
    private final int MEMORY_LIMIT;

    Judge0Api(String apiUrl, String sourceCode,
              int language, String stdin, String expectOutput, int timeLimit, int memoryLimit) {
        API_URL = apiUrl;
        SOURCE_CODE = sourceCode;
        LANGUAGE_ID = language;
        STDIN = stdin;
        EXPECT_OUTPUT = expectOutput;
        TIME_LIMIT = timeLimit;
        MEMORY_LIMIT = memoryLimit*1000;
    }

    @Override
    public String send() {
        Map<String, String> map = new HashMap<>();
        map.put("source_code", SOURCE_CODE);
        map.put("language_id", String.valueOf(LANGUAGE_ID));
        if (STDIN!=null)
            map.put("stdin", STDIN);

        if (EXPECT_OUTPUT!=null)
            map.put("expected_output", EXPECT_OUTPUT);
        map.put("cpu_time_limit", String.valueOf(TIME_LIMIT));
        map.put("memory_limit", String.valueOf(MEMORY_LIMIT));
        return HttpUtil.post(API_URL, convertParam(map));
    }
}
