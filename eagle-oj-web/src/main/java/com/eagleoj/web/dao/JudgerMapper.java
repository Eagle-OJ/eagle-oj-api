package com.eagleoj.web.dao;

import com.eagleoj.web.entity.JudgerEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface JudgerMapper {

    List<JudgerEntity> listAll();

    int save(JudgerEntity judgerEntity);

    int deleteByJid(int jid);

    int updateByJid(@Param("jid") int jid, @Param("data") JudgerEntity data);
}
