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

    Map<String, Object> getUserInfoByGidUid(@Param("gid") int gid, @Param("uid") int uid);

    List<Map<String, Object>> listUserJoinedGroups(int uid);

    int countByGid(int gid);

    int updateByGidUid(@Param("gid") int gid, @Param("uid") int uid, @Param("data") GroupUserEntity data);

    List<GroupUserEntity> listGroupMembersByGid(int gid);

    List<Map<String, Object>> listGroupMembersRankByGid(int gid);

    int deleteByGidUid(@Param("gid") int gid, @Param("uid") int uid);

    int deleteByGid(int gid);
}
