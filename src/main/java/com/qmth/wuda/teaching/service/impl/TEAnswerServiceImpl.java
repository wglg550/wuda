package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TEAnswer;
import com.qmth.wuda.teaching.dao.TEAnswerMapper;
import com.qmth.wuda.teaching.service.TEAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEAnswerServiceImpl extends ServiceImpl<TEAnswerMapper, TEAnswer> implements TEAnswerService {

    @Resource
    TEAnswerMapper teAnswerMapper;

    @Override
    public void deleteAll() {
        teAnswerMapper.deleteAll();
    }
}
