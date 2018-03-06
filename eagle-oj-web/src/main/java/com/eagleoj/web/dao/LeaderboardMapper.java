package com.eagleoj.web.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface LeaderboardMapper {
    List<Map<String, Object>> listOIRankByCid(@Param("cid") int cid, @Param("gid") int groupId, @Param("except") int exceptUser);

    List<Map<String, Object>> listACMRankByCid(@Param("cid") int cid, @Param("penalty") int penalty,@Param("gid") int groupId ,@Param("except") int exceptUser);
}
