package com.eagleoj.web.controller.format.user;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddProblemTestCaseFormat {

    @NotNull
    @Length(max = 65535)
    private String stdin;

    @NotNull
    @NotBlank
    @Length(max = 65535)
    private String stdout;

    @NotNull
    @Range(min = 1, max = 10)
    private Integer strength;

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }
}
