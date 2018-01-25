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

    List<Map<String, Object>> listValid();

    List<ContestEntity> listByUid(int uid);

    int deleteByCid(int cid);

    int updateDescriptionByCid(@Param("cid") int cid, @Param("data") ContestEntity data);

    int updateStatusByCid(@Param("cid") int cid, @Param("status") int status);

    ContestEntity getByCid(int cid);
}
