package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Share;
import java.util.List;

public interface ShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Share record);

    Share selectByPrimaryKey(Integer id);

    List<Share> selectAll();

    int updateByPrimaryKey(Share record);
}