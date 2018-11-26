package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Introduction;
import java.util.List;

public interface IntroductionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Introduction record);

    List<Introduction> selectAll();
}