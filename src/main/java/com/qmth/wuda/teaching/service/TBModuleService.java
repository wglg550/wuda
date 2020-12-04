package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TBModule;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 模块信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBModuleService extends IService<TBModule> {

    void deleteAll();
}
