package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.MoneyLog;
import java.util.List;

public interface MoneyLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyLog record);

    MoneyLog selectByPrimaryKey(Integer id);

    List<MoneyLog> selectAll();

    int updateByPrimaryKey(MoneyLog record);
}