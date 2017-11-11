package org.inlighting.oj.web.service;

import org.inlighting.oj.web.entity.GroupEntity;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class GroupService {

    public int createGroup(int owner, int cover, String name, String password, long createTime) {
        // todo
        return 0;
    }

    public GroupEntity getByGid(int gid) {
        // todo
        return null;
    }
}
