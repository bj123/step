package com.aiwsport.core.cache;

import com.aiwsport.core.entity.ShowConfig;
import com.aiwsport.core.mapper.ShowConfigMapper;
import com.aiwsport.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yangjian9 on 2018/11/29.
 */
@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    private ShowConfigMapper showConfigMapper;

    @Override
    public void run(String... args) throws Exception {
        List<ShowConfig> showConfigs = showConfigMapper.selectAll();
        for (ShowConfig showConfig : showConfigs) {
            CommonUtil.storyConfig.put(showConfig.getConfigname(), showConfig.getConfigvalue());
        }
    }
}
