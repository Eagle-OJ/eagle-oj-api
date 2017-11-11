package org.inlighting.oj.web.service;

import org.inlighting.oj.web.entity.ContestUserInfoEntity;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class ContestUserInfoService {

    /**
     * 联合主键（cid,uid），所以没有自增主键
     */
    public boolean add(int cid, int uid) {
        // todo
        return false;
    }

    public ContestUserInfoEntity getByCidAndUid(int cid, int uid) {
        // todo
        return null;
    }


}
