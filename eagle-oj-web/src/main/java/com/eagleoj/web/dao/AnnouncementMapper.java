package com.eagleoj.web.dao;

import com.eagleoj.web.entity.AnnouncementEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface AnnouncementMapper {
    int save(AnnouncementEntity data);

    List<AnnouncementEntity> listAll();

    int deleteByAid(int aid);

    int updateByAid(@Param("aid") int aid, @Param("data") AnnouncementEntity data);
}
