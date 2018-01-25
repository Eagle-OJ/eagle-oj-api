package com.eagleoj.web.dao;

import com.eagleoj.web.entity.ContestProblemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface ContestProblemMapper {
    int save(ContestProblemEntity contestProblemEntity);

    ContestProblemEntity getByCidPid(@Param("cid") int cid, @Param("pid") int pid);

    ContestProblemEntity getByCidDisplayId(@Param("cid") int cid, @Param("displayId") int displayId);

    List<Map<String, Object>> listByCid(int cid);

    List<Map<String, Object>> listByCidUidWithStatus(@Param("cid") int cid, @Param("uid") int uid);

    int updateByCidPid(@Param("cid") int cid, @Param("pid") int pid, @Param("data") ContestProblemEntity data);

    int deleteByCidPid(@Param("cid") int cid, @Param("pid") int pid);
}
