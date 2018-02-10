package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.JudgerMapper;
import com.eagleoj.web.entity.JudgerEntity;
import com.eagleoj.web.service.JudgerService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class JudgerServiceImpl implements JudgerService {

    @Autowired
    private JudgerMapper judgerMapper;

    @Override
    public List<JudgerEntity> listJudger() {
        return judgerMapper.listAll();
    }

    @Override
    public void addJudger(String url, int port) {
        JudgerEntity judgerEntity = new JudgerEntity();
        judgerEntity.setUrl(url);
        judgerEntity.setPort(port);
        judgerEntity.setAddTime(System.currentTimeMillis());
        boolean flag = judgerMapper.save(judgerEntity) == 1;
        WebUtil.assertIsSuccess(flag, "判卷机添加失败");
    }

    @Override
    public void deleteJudger(int jid) {
        boolean flag = judgerMapper.deleteByJid(jid) == 1;
        WebUtil.assertIsSuccess(flag, "判卷机删除失败");
    }

    @Override
    public void updateJudger(int jid, String url, Integer port) {
        JudgerEntity entity = new JudgerEntity();
        entity.setUrl(url);
        entity.setPort(port);
        boolean flag = judgerMapper.updateByJid(jid, entity) == 1;
        WebUtil.assertIsSuccess(flag, "判卷机更新失败");
    }
}
