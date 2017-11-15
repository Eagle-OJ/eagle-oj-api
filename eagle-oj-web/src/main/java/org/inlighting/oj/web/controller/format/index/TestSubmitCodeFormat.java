package org.inlighting.oj.web.controller.format.index;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import org.inlighting.oj.judge.config.CodeLanguageEnum;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class TestSubmitCodeFormat {

    @NotNull
    @JSONField(name = "problem_id")
    private Integer problemId;

    @NotNull
    @JSONField(name = "code_language")
    private CodeLanguageEnum codeLanguage;

    @NotNull
    @JSONField(name = "code_source")
    private String codeSource;

    @NotNull
    @JSONField(name = "test_cases")
    private JSONArray testCases;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public CodeLanguageEnum getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(CodeLanguageEnum codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public String getCodeSource() {
        return codeSource;
    }

    public void setCodeSource(String codeSource) {
        this.codeSource = codeSource;
    }

    public JSONArray getTestCases() {
        return testCases;
    }

    public void setTestCases(JSONArray testCases) {
        this.testCases = testCases;
    }
}
