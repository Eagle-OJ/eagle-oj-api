package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.config.CodeLanguageEnum;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class SubmitCodeFormat {

    @Range(min = 1)
    @JSONField(name = "contest_id")
    private Integer contestId;

    @NotNull
    @Range(min = 1)
    @JSONField(name = "problem_id")
    private Integer problemId;

    @NotNull
    @JSONField(name = "lang")
    private LanguageEnum lang;

    @NotNull
    @NotBlank
    @JSONField(name = "source_code")
    private String SourceCode;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public LanguageEnum getLang() {
        return lang;
    }

    public void setLang(LanguageEnum lang) {
        this.lang = lang;
    }

    public String getSourceCode() {
        return SourceCode;
    }

    public void setSourceCode(String sourceCode) {
        SourceCode = sourceCode;
    }
}
