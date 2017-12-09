package org.inlighting.oj.judge.judger;

import org.inlighting.oj.judge.entity.ResponseEntity;

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
