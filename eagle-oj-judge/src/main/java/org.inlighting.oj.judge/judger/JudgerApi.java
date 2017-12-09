package org.inlighting.oj.judge.judger;

import org.inlighting.oj.judge.entity.RequestEntity;
import org.inlighting.oj.judge.entity.ResponseEntity;

/**
 * @author Smith
 **/
public interface JudgerApi {
    ResponseEntity judge(String url, RequestEntity requestEntity) throws Exception;
}
