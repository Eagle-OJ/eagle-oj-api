package com.eagleoj.web.dao;

import com.eagleoj.web.entity.ContestProblemUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface ContestProblemUserMapper {
    int save(ContestProblemUserEntity data);

    int countByCidPid(@Param("cid") int cid, @Param("pid") int pid);

    ContestProblemUserEntity getByCidPidUid(@Param("cid") int cid, @Param("pid") int pid, @Param("uid")int uid);

    List<Map<String, Object>> listByCidUid(@Param("cid") int cid, @Param("uid") int uid);

    List<ContestProblemUserEntity> listAllByCid(int cid);

    int updateByCidPidUid(@Param("cid") int cid, @Param("pid") int pid,
                          @Param("uid") int uid, @Param("data") ContestProblemUserEntity data);
}
