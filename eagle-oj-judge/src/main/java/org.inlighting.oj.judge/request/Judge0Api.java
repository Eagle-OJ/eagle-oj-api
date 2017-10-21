package org.inlighting.oj.judge.request;

import java.util.HashMap;
import java.util.Map;

class Judge0Api implements RequestBase {

    private final String API_URL = "http://localhost:3000/submissions?wait=true";
    //private final String API_URL = "https://api.judge0.com/submissions?wait=true";

    private String SOURCE_CODE;

    private int LANGUAGE_ID;

    private String STDIN;

    private String EXPECT_OUTPUT;

    // second
    private int TIME_LIMIT;

    // kb
    private int MEMORY_LIMIT;

    Judge0Api(String sourceCode, int language, String stdin, String expectOutput, int timeLimit, int memoryLimit) {
        SOURCE_CODE = sourceCode;
        LANGUAGE_ID = language;
        STDIN = stdin;
        EXPECT_OUTPUT = expectOutput;
        TIME_LIMIT = timeLimit;
        MEMORY_LIMIT = memoryLimit;
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
