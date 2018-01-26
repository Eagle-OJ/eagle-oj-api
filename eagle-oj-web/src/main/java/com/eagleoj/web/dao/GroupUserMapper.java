package com.eagleoj.web.dao;

import com.eagleoj.web.entity.GroupUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface GroupUserMapper {
    int save(GroupUserEntity groupUserEntity);

    GroupUserEntity getByGidUid(@Param("gid") int gid, @Param("uid") int uid);

    List<Map<String, Object>> listUserJoinedGroups(int uid);

    int updateByGidUid(@Param("gid") int gid, @Param("uid") int uid, @Param("data") GroupUserEntity data);

    List<Map<String, Object>> listGroupMembersByGid(int gid);

    int deleteGroupMemberByGidUid(@Param("gid") int gid, @Param("uid") int uid);
}
