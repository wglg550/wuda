package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEAnswerMapper;
import com.qmth.wuda.teaching.entity.TEAnswer;
import com.qmth.wuda.teaching.service.TEAnswerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    /**
     * 根据考试记录id删除答案
     *
     * @param examRecordId
     */
    @Override
    public void deleteAll(Long examRecordId) {
        teAnswerMapper.deleteAll(examRecordId);
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

    /**
     * 根据维度求学院该维度的平均值
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @param dimension
     * @return
     */
    @Override
    @Cacheable(value = "calculate_college_avg_score_cache", key = "#schoolId + '-' + #examId + '-' + #collegeId + '-' + #courseCode + '-' + #dimension", unless = "#result == null")
    public BigDecimal calculateCollegeAvgScoreByDimension(Long schoolId, Long examId, Long collegeId, String courseCode, String dimension) {
        return teAnswerMapper.calculateCollegeAvgScoreByDimension(schoolId, examId, collegeId, courseCode, dimension);
    }
}
