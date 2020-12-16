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
     * 根据试卷类型和科目编码删除
     *
     * @param paperCode
     * @param courseCode
     * @param moduleNames
     */
    void deleteAll(String paperCode, String courseCode, Set<String> moduleNames);

    /**
     * 根据试卷类型和科目编码获取模块
     *
     * @param paperCode
     * @param courseCode
     * @return
     */
    List<TBModule> findByPaperCodeAndCourseCode(String paperCode, String courseCode);

    /**
     * 根据试卷类型和科目编码更新模块
     *
     * @param paperCode
     * @param courseCode
     * @return
     */
    List<TBModule> updateByPaperCodeAndCourseCode(String paperCode, String courseCode);

    /**
     * 根据试卷类型和科目编码删除模块
     *
     * @param paperCode
     * @param courseCode
     */
    void deleteByPaperCodeAndCourseCode(String paperCode, String courseCode);
}
