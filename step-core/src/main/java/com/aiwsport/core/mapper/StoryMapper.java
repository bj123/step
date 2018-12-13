package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Story;
import java.util.List;
import java.util.Map;

public interface StoryMapper {
    int deleteByPrimaryKey(Integer id);

    Story getPlayInfo(Map<String,Object> map);

    int insert(Story record);

    Story selectByPrimaryKey(Integer id);

    List<Story> selectAll();

    int updateByPrimaryKey(Story record);

    List<Story> getStroysByTemplateId(Integer templateId);
}