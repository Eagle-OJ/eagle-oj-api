package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;
import org.inlighting.oj.judge.config.CodeLanguageEnum;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class SubmitCodeFormat {

    @NotNull
    @JSONField(name = "contest_id")
    private Integer contestId;

    @NotNull
    @JSONField(name = "problem_id")
    private Integer problemId;

    @NotNull
    @JSONField(name = "code_language")
    private CodeLanguageEnum codeLanguage;

    @NotNull
    @JSONField(name = "code_source")
    private String codeSource;

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
}
