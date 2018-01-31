package com.eagleoj.web.dao;

import com.eagleoj.web.entity.ContestUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface ContestUserMapper {
    int save(ContestUserEntity entity);

    ContestUserEntity getByCidUid(@Param("cid") int cid, @Param("uid") int uid);

    List<Map<String, Object>> listUserJoinedContestsByUid(int uid);

    int updateByCidUid(@Param("cid") int cid, @Param("uid") int uid, @Param("data") ContestUserEntity data);
}
