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
     * 根据科目编码删除
     *
     * @param courseCode
     * @param moduleNames
     */
    void deleteAll(String courseCode, Set<String> moduleNames);

    /**
     * 根据科目编码获取模块
     *
     * @param courseCode
     * @return
     */
    List<TBModule> findByCourseCode(String courseCode);

    /**
     * 根据科目编码更新模块
     *
     * @param courseCode
     * @return
     */
    List<TBModule> updateByCourseCode(String courseCode);

    /**
     * 根据科目编码删除模块
     *
     * @param courseCode
     */
    void deleteByCourseCode(String courseCode);
}
