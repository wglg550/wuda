package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEQuestionMapper;
import com.qmth.wuda.teaching.entity.TEQuestion;
import com.qmth.wuda.teaching.service.TEQuestionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 根据试卷id删除题目
     *
     * @param paperId
     */
    @Override
    @CacheEvict(value = "question_cache", key = "#paperId")
    public void deleteAll(Long paperId) {
        teQuestionMapper.deleteAll(paperId);
    }

    /**
     * 根据试卷id查询题目
     *
     * @param paperId
     * @return
     */
    @Override
    @Cacheable(value = "question_cache", key = "#paperId", unless = "#result == null")
    public List<TEQuestion> findByPaperId(Long paperId) {
        return teQuestionMapper.findByPaperId(paperId);
    }
}
