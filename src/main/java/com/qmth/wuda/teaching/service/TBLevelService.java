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
     * 查询所有等级
     *
     * @return
     */
    List<TBLevel> findAll();
}
