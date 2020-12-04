package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TBDimension;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 维度信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBDimensionService extends IService<TBDimension> {

    void deleteAll();
}
