package com.eagleoj.judge.judger;

import com.eagleoj.judge.entity.ResponseEntity;

/**
 * @author Smith
 **/
abstract class AbstractJudger {
    JudgerApi judgerApi;

    AbstractJudger(JudgerApi judgerApi) {
        this.judgerApi = judgerApi;
    }

    abstract ResponseEntity judge();
}
