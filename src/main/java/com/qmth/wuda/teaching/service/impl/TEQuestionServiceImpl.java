package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TEQuestion;
import com.qmth.wuda.teaching.dao.TEQuestionMapper;
import com.qmth.wuda.teaching.service.TEQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 试卷结构题型说明 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEQuestionServiceImpl extends ServiceImpl<TEQuestionMapper, TEQuestion> implements TEQuestionService {

    @Resource
    TEQuestionMapper teQuestionMapper;

    @Override
    public void deleteAll() {
        teQuestionMapper.deleteAll();
    }
}
