package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TBMajor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 专业信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBMajorService extends IService<TBMajor> {

    void deleteAll();
}
