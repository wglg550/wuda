package com.qmth.wuda.teaching.start;

import com.qmth.wuda.teaching.constant.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Description: 服务启动时初始化运行，哪个微服务模块需要则拿此模版去用
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/7/3
 */
@Component
public class StartRunning implements CommandLineRunner {
    private final static Logger log = LoggerFactory.getLogger(StartRunning.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("服务器启动时执行 start");
        SystemConstant.initTempFiles();
        log.info("服务器启动时执行 end");
    }
}
