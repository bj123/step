package com.aiwsport.web.controller;

import com.aiwsport.core.constant.ResultMsg;
import com.aiwsport.core.service.StepService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务操作
 *
 * @author yangjian
 */
@RestController
public class ServerController {

    @Autowired
    private StepService stepService;

    private static Logger logger = LogManager.getLogger();

    @RequestMapping("/test.json")
    public ResultMsg test() throws Exception{
        return new ResultMsg("服务启动成功", 9276);
    }

}
