package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.ContestUserMapper;
import com.eagleoj.web.entity.ContestUserEntity;
import com.eagleoj.web.service.ContestUserService;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestUserServiceImpl implements ContestUserService {

    @Autowired
    private ContestUserMapper contestUserMapper;

    /**
     * 联合主键（cid,uid），所以没有自增主键
     */
    @Override
    public boolean save(int cid, int uid) {
        //添加contestUserInfo
        ContestUserEntity contestUserEntity = new ContestUserEntity();
        contestUserEntity.setCid(cid);
        contestUserEntity.setUid(uid);
        contestUserEntity.setJoinTime(System.currentTimeMillis());
        return contestUserMapper.save(contestUserEntity) == 1;
    }

    @Override
    public ContestUserEntity get(int cid, int uid) {
        // 通过cid和uid来获取实体
        return contestUserMapper.getByCidUid(cid, uid);
    }

    @Override
    public List<Map<String, Object>> listUserContests(int uid) {
        return contestUserMapper.listUserContestsByUid(uid);
    }

    @Override
    public List<Map<String, Object>> listNormalContestRank(int cid) {
        return contestUserMapper.listNormalContestRankByCid(cid);
    }

    @Override
    public List<Map<String, Object>> listACMContestRank(int cid, int penalty) {
        return contestUserMapper.listACMContestRankByCid(cid, penalty);
    }

    @Override
    public boolean updateByCidUid(int cid, int uid, ContestUserEntity entity) {
        return contestUserMapper.updateByCidUid(cid, uid, entity) == 1;
    }
}
