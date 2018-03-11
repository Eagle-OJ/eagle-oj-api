package com.eagleoj.web.dao;

import com.eagleoj.web.entity.ContestEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface ContestMapper {
    int save(ContestEntity entity);

    int countByGid(int gid);

    int count();

    List<ContestEntity> listByUidGid(@Param("uid") int uid, @Param("gid") int gid);

    List<Map<String, Object>> listContests(@Param("isAll") boolean isAll);

    List<ContestEntity> listContestsByGid(@Param("gid") int gid, @Param("status") Integer status);

    int deleteByCid(int cid);

    int updateDescriptionByCid(@Param("cid") int cid, @Param("data") ContestEntity data);

    int updateStatusByCid(@Param("cid") int cid, @Param("status") int status);

    int updateByCid(@Param("cid") int cid, @Param("data") ContestEntity contestEntity);

    ContestEntity getByCid(int cid);
}
