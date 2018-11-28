package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.ShowConfig;
import java.util.List;

public interface ShowConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShowConfig record);

    ShowConfig selectByPrimaryKey(Integer id);

    List<ShowConfig> selectAll();

    int updateByPrimaryKey(ShowConfig record);
}