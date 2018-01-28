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

    List<Map<String, Object>> listNormalContestRankByCid(int cid);

    List<Map<String, Object>> listACMContestRankByCid(@Param("cid") int cid, @Param("penalty") int penalty);

    int updateByCidUid(@Param("cid") int cid, @Param("uid") int uid, @Param("data") ContestUserEntity data);
}
