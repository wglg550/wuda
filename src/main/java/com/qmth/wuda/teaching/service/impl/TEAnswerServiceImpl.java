package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEAnswerMapper;
import com.qmth.wuda.teaching.entity.TEAnswer;
import com.qmth.wuda.teaching.service.TEAnswerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
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

    /**
     * 根据考试记录id查找答题记录
     *
     * @param examRecordId
     * @return
     */
    @Override
    @Cacheable(value = "answer_cache", key = "#examRecordId", unless = "#result == null")
    public List<TEAnswer> findByExamRecordId(Long examRecordId) {
        return teAnswerMapper.findByExamRecordId(examRecordId);
    }
}
