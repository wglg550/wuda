package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBModule;

import java.util.List;

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

    /**
     * 根据学校id获取模块
     *
     * @param schoolId
     * @return
     */
    List<TBModule> findBySchoolId(Long schoolId);
}
