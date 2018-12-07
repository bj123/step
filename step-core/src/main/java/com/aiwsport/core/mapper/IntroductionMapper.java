package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Introduction;
import java.util.List;

public interface IntroductionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Introduction record);

    Introduction selectByPrimaryKey(Integer id);

    List<Introduction> selectAll();

    int updateByPrimaryKey(Introduction record);

    List<Introduction> getIntroductionsByTemplateId(Integer templateId);
}