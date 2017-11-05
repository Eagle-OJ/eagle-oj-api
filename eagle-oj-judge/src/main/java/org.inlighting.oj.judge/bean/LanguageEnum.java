package org.inlighting.oj.judge.bean;

import com.alibaba.fastjson.JSON;

public enum LanguageEnum {
    JAVA8(3, 500),
    C(3, 500),
    PYTHON36(3, 500),
    CPP(3, 500);

    private int defaultTime;
    private int defaultMemory;

    private LanguageEnum(int defaultTime, int defaultMemory) {
        this.defaultTime = defaultTime;
        this.defaultMemory = defaultMemory;
    }

    public int getDefaultTime() {
        return defaultTime;
    }

    public int getDefaultMemory() {
        return defaultMemory;
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(LanguageEnum.C));
    }
}
