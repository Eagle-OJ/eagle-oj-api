package org.inlighting.oj.judge.request;

import java.util.Map;

public interface RequestBase {

    String send();

    default String convertParam(Map<String, String> param) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry: param.entrySet()) {
            stringBuilder = stringBuilder.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }
}
