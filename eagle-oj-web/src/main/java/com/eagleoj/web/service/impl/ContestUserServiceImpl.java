package com.eagleoj.web.service.impl;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.ContestUserMapper;
import com.eagleoj.web.dao.GroupUserMapper;
import com.eagleoj.web.entity.ContestEntity;
import com.eagleoj.web.entity.ContestUserEntity;
import com.eagleoj.web.entity.GroupUserEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.ContestService;
import com.eagleoj.web.service.ContestUserService;
import com.eagleoj.web.service.GroupUserService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ContestService contestService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupUserService groupUserService;

    @Override
    public ContestUserEntity get(int cid, int uid) {
        ContestUserEntity contestUserEntity = contestUserMapper.getByCidUid(cid, uid);
        WebUtil.assertNotNull(contestUserEntity, "你没有加入比赛");
        return contestUserEntity;
    }

    @Override
    public List<Map<String, Object>> listUserJoinedContests(int uid) {
        return contestUserMapper.listUserJoinedContestsByUid(uid);
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

    @Transactional
    @Override
    public void joinContest(int cid, int uid, String password) {
        ContestUserEntity contestUserEntity = contestUserMapper.getByCidUid(cid, uid);
        WebUtil.assertNull(contestUserEntity, "你已经加入比赛");

        ContestEntity contestEntity = contestService.getContest(cid);
        if (contestEntity.getGroup() > 0) {
            // 小组赛
            boolean flag = groupUserService.isUserInGroup(contestEntity.getGroup(), uid);
            WebUtil.assertIsSuccess(flag, "你不在本小组中，无法加入此比赛");
        } else {
            // 非小组赛
            // 校验密码
            if (contestEntity.getPassword() != null) {
                String originPassword = contestEntity.getPassword();
                if (! password.equals(originPassword)) {
                    throw new WebErrorException("密码错误");
                }
            }
        }


        ContestUserEntity newContestUserEntity = new ContestUserEntity();
        newContestUserEntity.setCid(cid);
        newContestUserEntity.setUid(uid);
        newContestUserEntity.setJoinTime(System.currentTimeMillis());
        WebUtil.assertIsSuccess(contestUserMapper.save(newContestUserEntity) == 1, "比赛加入失败");

        // 添加加入比赛的记录
        UserEntity userEntity = new UserEntity();
        userEntity.setContestTimes(1);
        userService.updateUser(uid, userEntity);
    }
}
