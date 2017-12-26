package org.inlighting.oj.judge;

/**
 * @author Smith
 **/
public enum ResultEnum {
    AC("Accepted"), WA("Wrong Answer"), RTE("Runtime Error"),
    TLE("Time Limit Exceed"), CE("Compile Error"), SE("Server Error");

    private String name;

    ResultEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
