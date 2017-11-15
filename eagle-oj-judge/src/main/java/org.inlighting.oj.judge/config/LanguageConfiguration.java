package org.inlighting.oj.judge.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
public class LanguageConfiguration {
    private static Map<CodeLanguageEnum, Map<String, Integer>> languageMap;

    private final static String LIMIT_TIME_FILED = "limit_time";

    private final static String LIMIT_MEMORY_FILED = "limit_memory";

    static {
        languageMap = new HashMap<>();
        Map<String, Integer> JAVA8Configuration = new HashMap<>();
        JAVA8Configuration.put(LIMIT_TIME_FILED, 3);
        JAVA8Configuration.put(LIMIT_MEMORY_FILED, 128);

        Map<String, Integer> CPPConfiguration = new HashMap<>();
        CPPConfiguration.put(LIMIT_TIME_FILED, 3);
        CPPConfiguration.put(LIMIT_MEMORY_FILED, 128);

        Map<String, Integer> CConfiguration = new HashMap<>();
        CConfiguration.put(LIMIT_TIME_FILED, 3);
        CConfiguration.put(LIMIT_MEMORY_FILED, 128);

        Map<String, Integer> PYTHON27Configuration = new HashMap<>();
        PYTHON27Configuration.put(LIMIT_TIME_FILED, 3);
        PYTHON27Configuration.put(LIMIT_MEMORY_FILED, 128);

        Map<String, Integer> PYTHON36Configuration = new HashMap<>();
        PYTHON36Configuration.put(LIMIT_TIME_FILED, 3);
        PYTHON36Configuration.put(LIMIT_MEMORY_FILED, 128);

        languageMap.put(CodeLanguageEnum.JAVA8, JAVA8Configuration);
        languageMap.put(CodeLanguageEnum.CPP, CPPConfiguration);
        languageMap.put(CodeLanguageEnum.C, CConfiguration);
        languageMap.put(CodeLanguageEnum.PYTHON27, PYTHON27Configuration);
        languageMap.put(CodeLanguageEnum.PYTHON36, PYTHON36Configuration);
    }

    public static Map<CodeLanguageEnum, Map<String, Integer>> getLanguageMap() {
        return languageMap;
    }
}
