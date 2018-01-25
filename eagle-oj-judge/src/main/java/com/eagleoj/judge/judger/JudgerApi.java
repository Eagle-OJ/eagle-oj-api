package com.eagleoj.judge.judger;

import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.judge.entity.RequestEntity;

/**
 * @author Smith
 **/
public interface JudgerApi {
    ResponseEntity judge(String url, RequestEntity requestEntity) throws Exception;
}
