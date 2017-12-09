package org.inlighting.oj.web.controller.format.index;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Range;
import org.inlighting.oj.judge.LanguageEnum;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class SubmitCodeFormat {

    @NotNull
    private LanguageEnum lang;

    @JSONField(name = "source_code")
    @NotNull
    private String sourceCode;

    @JSONField(name = "test_cases")
    @NotNull
    private JSONArray testCases;

    public LanguageEnum getLang() {
        return lang;
    }

    public void setLang(LanguageEnum lang) {
        this.lang = lang;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public JSONArray getTestCases() {
        return testCases;
    }

    public void setTestCases(JSONArray testCases) {
        this.testCases = testCases;
    }
}
