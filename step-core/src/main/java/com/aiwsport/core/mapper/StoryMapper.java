package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Story;
import java.util.List;

public interface StoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Story record);

    Story selectByPrimaryKey(Integer id);

    List<Story> selectAll();

    int updateByPrimaryKey(Story record);
}