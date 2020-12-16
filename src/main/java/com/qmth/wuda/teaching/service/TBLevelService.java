package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBLevel;

import java.util.List;

/**
 * <p>
 * 等级信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBLevelService extends IService<TBLevel> {

    /**
     * 根据模块id删除等级
     *
     * @param moduleId
     */
    void deleteAll(Long moduleId);

    /**
     * 根据模块id查询所有等级
     *
     * @param moduleId
     * @return
     */
    List<TBLevel> findByModuleId(Long moduleId);
}
