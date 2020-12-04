package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TEExamRecord;
import com.qmth.wuda.teaching.dao.TEExamRecordMapper;
import com.qmth.wuda.teaching.service.TEExamRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 考试记录表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEExamRecordServiceImpl extends ServiceImpl<TEExamRecordMapper, TEExamRecord> implements TEExamRecordService {

    @Resource
    TEExamRecordMapper teExamRecordMapper;

    @Override
    public void deleteAll() {
        teExamRecordMapper.deleteAll();
    }
}
