package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TEAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEAnswerService extends IService<TEAnswer> {

    void deleteAll();
}
