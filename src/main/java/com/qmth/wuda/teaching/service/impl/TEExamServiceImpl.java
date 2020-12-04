package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TEExam;
import com.qmth.wuda.teaching.dao.TEExamMapper;
import com.qmth.wuda.teaching.service.TEExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 考试批次表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEExamServiceImpl extends ServiceImpl<TEExamMapper, TEExam> implements TEExamService {

    @Resource
    TEExamMapper teExamMapper;

    @Override
    public void deleteAll() {
        teExamMapper.deleteAll();
    }
}
