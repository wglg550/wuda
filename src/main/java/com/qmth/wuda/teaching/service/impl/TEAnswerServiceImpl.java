package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEAnswerMapper;
import com.qmth.wuda.teaching.entity.TEAnswer;
import com.qmth.wuda.teaching.service.TEAnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
//    @Cacheable(value = "answer_cache", key = "#examRecordId", unless = "#result == null")
    public List<TEAnswer> findByExamRecordId(Long examRecordId) {
        return teAnswerMapper.findByExamRecordId(examRecordId);
    }

    /**
     * 根据维度求学院该维度的平均值
     *
     * @param examId
     * @param paperId
     * @param collegeId
     * @param courseCode
     * @param dimensions
     * @return
     */
    @Override
//    @Cacheable(value = "calculate_college_avg_score_cache", key = "#examId + '-' + #collegeId + '-' + #courseCode + '-' + #dimensions + '-' + #type", unless = "#result == null")
    public BigDecimal calculateCollegeAvgScoreByDimension(Long examId, Long paperId, Long collegeId, String courseCode, Set<String> dimensions, String moduleCode) {
        return teAnswerMapper.calculateCollegeAvgScoreByDimension(examId, paperId, collegeId, courseCode, dimensions, moduleCode);
    }

    /**
     * 根据维度求学生该维度的平均值
     *
     * @param examId
     * @param paperId
     * @param collegeId
     * @param studentCode
     * @param courseCode
     * @param dimensions
     * @param moduleCode
     * @return
     */
    @Override
    public BigDecimal calculateStudentAvgScoreByDimension(Long examId, Long paperId, Long collegeId, String studentCode, String courseCode, Set<String> dimensions, String moduleCode) {
        return teAnswerMapper.calculateStudentAvgScoreByDimension(examId, paperId, collegeId, studentCode, courseCode, dimensions, moduleCode);
    }
}
