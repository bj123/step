package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Template;
import java.util.List;

public interface TemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Template record);

    Template selectByPrimaryKey(Integer id);

    List<Template> selectAll();

    int updateByPrimaryKey(Template record);
}