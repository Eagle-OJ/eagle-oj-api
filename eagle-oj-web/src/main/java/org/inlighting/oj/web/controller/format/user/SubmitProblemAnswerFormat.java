package org.inlighting.oj.web.controller.format.user;

import org.hibernate.validator.constraints.NotBlank;
import org.inlighting.oj.judge.config.LanguageEnum;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class SubmitProblemAnswerFormat {

    @NotNull
    private LanguageEnum languageEnum;

    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LanguageEnum getLanguageEnum() {
        return languageEnum;
    }

    public void setLanguageEnum(LanguageEnum languageEnum) {
        this.languageEnum = languageEnum;
    }
}
