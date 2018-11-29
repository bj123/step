package com.aiwsport.web.task;

import com.aiwsport.core.service.SysInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 活动奖励检查
 *
 * @author yangjian9
 */
@Configuration
@EnableScheduling
public class ReFreshTask {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private SysInfoService sysInfoService;

    @Scheduled(cron = "0 0/30 * * * ?") // 每30分钟执行一次
    public void RefreshStoryConfig(){
        sysInfoService.refreshStoryConfig();
    }

}
