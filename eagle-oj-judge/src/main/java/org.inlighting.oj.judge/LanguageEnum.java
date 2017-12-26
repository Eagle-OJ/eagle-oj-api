package org.inlighting.oj.judge;

/**
 * @author Smith
 **/
public enum LanguageEnum {
    JAVA8("Java8"), PYTHON27("Python2.7"), PYTHON36("Python3.6"), CPP("C++"), C("C");

    private String name;

    private LanguageEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
