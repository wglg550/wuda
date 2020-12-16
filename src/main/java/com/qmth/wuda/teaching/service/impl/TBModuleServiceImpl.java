package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TBModuleMapper;
import com.qmth.wuda.teaching.entity.TBModule;
import com.qmth.wuda.teaching.service.TBModuleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 模块信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBModuleServiceImpl extends ServiceImpl<TBModuleMapper, TBModule> implements TBModuleService {

    @Resource
    TBModuleMapper tbModuleMapper;

    /**
     * 根据试卷类型和科目编码删除
     *
     * @param paperCode
     * @param courseCode
     * @param moduleNames
     */
    @Override
    public void deleteAll(String paperCode, String courseCode, Set<String> moduleNames) {
        tbModuleMapper.deleteAll(paperCode, courseCode, moduleNames);
    }

    /**
     * 根据试卷类型和科目编码获取模块
     *
     * @param paperCode
     * @param courseCode
     * @return
     */
    @Override
    @Cacheable(value = "module_cache", key = "#paperCode + '-' + #courseCode", unless = "#result == null")
    public List<TBModule> findByPaperCodeAndCourseCode(String paperCode, String courseCode) {
        return tbModuleMapper.findByPaperCodeAndCourseCode(paperCode, courseCode);
    }

    /**
     * 根据试卷类型和科目编码更新模块
     *
     * @param paperCode
     * @param courseCode
     * @return
     */
    @Override
    @CachePut(value = "module_cache", key = "#paperCode + '-' + #courseCode", condition = "#result != null")
    public List<TBModule> updateByPaperCodeAndCourseCode(String paperCode, String courseCode) {
        return tbModuleMapper.findByPaperCodeAndCourseCode(paperCode, courseCode);
    }

    /**
     * 根据试卷类型和科目编码删除模块
     *
     * @param paperCode
     * @param courseCode
     */
    @Override
    @CacheEvict(value = "module_cache", key = "#paperCode + '-' + #courseCode")
    public void deleteByPaperCodeAndCourseCode(String paperCode, String courseCode) {

    }
}
