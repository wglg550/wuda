package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBModule;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 模块信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBModuleService extends IService<TBModule> {

    /**
     * 根据学校id和模块名称删除
     *
     * @param schoolId
     * @param moduleNames
     */
    void deleteAll(Long schoolId, Set<String> moduleNames);

    /**
     * 根据学校id获取模块
     *
     * @param schoolId
     * @return
     */
    List<TBModule> findBySchoolId(Long schoolId);

    /**
     * 根据学校id更新模块
     *
     * @param schoolId
     * @return
     */
    List<TBModule> updateBySchoolId(Long schoolId);

    /**
     * 根据学校id删除模块
     *
     * @param schoolId
     */
    void deleteBySchoolId(Long schoolId);
}
