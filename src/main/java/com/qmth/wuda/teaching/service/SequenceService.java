package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.Sequence;

/**
 * @Description: SequenceService接口
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/15
 */
public interface SequenceService extends IService<Sequence> {

    /**
     * 获取序列号
     *
     * @return
     */
    Integer selectNextVal();
}
