package com.qmth.wuda.teaching.config;

import com.qmth.wuda.teaching.domain.SysDomain;
import com.qmth.wuda.teaching.domain.YunMarkDomain;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 数据字典
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/10
 */
@Configuration
public class DictionaryConfig {

    /**
     * 系统配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "sys.config", ignoreUnknownFields = false)
    public SysDomain sysDomain() {
        return new SysDomain();
    }

    /**
     * 云阅卷配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "yun.mark", ignoreUnknownFields = false)
    public YunMarkDomain yunMarkDomain() {
        return new YunMarkDomain();
    }
}
