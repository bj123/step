package com.aiwsport.core.service;

import com.aiwsport.core.entity.ShowConfig;
import com.aiwsport.core.mapper.ShowConfigMapper;
import com.aiwsport.core.utils.CommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yangjian9 on 2018/11/29.
 */
@Service
public class SysInfoService {
    @Autowired
    private ShowConfigMapper showConfigMapper;

    private static Logger logger = LogManager.getLogger();

    public ConcurrentMap<String, String> getStoryConfig() {
        return CommonUtil.storyConfig;
    }

    public ConcurrentMap<String, String> refreshStoryConfig() {
        List<ShowConfig> showConfigs = showConfigMapper.selectAll();
        for (ShowConfig showConfig : showConfigs) {
            CommonUtil.storyConfig.put(showConfig.getConfigname(), showConfig.getConfigvalue());
        }
        return CommonUtil.storyConfig;
    }
}
