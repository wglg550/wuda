package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.SequenceMapper;
import com.qmth.wuda.teaching.entity.Sequence;
import com.qmth.wuda.teaching.service.SequenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: SequenceServiceImpl接口
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/15
 */
@Service
public class SequenceServiceImpl extends ServiceImpl<SequenceMapper, Sequence> implements SequenceService {

    @Resource
    SequenceMapper sequenceMapper;

    /**
     * 获取序列号
     *
     * @return
     */
    @Override
    public Integer selectNextVal() {
        return sequenceMapper.selectNextVal();
    }
}
