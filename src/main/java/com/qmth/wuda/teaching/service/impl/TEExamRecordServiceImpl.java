package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.bean.report.SynthesisBean;
import com.qmth.wuda.teaching.dao.TEExamRecordMapper;
import com.qmth.wuda.teaching.entity.TEExamRecord;
import com.qmth.wuda.teaching.service.TEExamRecordService;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 根据考试id和考生id和试卷id删除考试记录
     *
     * @param examId
     * @param examStudentId
     * @param paperId
     */
    @Override
    public void deleteAll(Long examId, Long examStudentId, Long paperId) {
        teExamRecordMapper.deleteAll(examId, examStudentId, paperId);
    }

    /**
     * 获取学院分数
     *
     * @param examId
     * @param collegeId
     * @param courseCode
     * @return
     */
    @Override
//    @Cacheable(value = "college_score_cache", key = "#examId + '-' + #collegeId + '-' + #courseCode", unless = "#result == null")
    public SynthesisBean findByCollegeScore(Long examId, Long collegeId, String courseCode) {
        return teExamRecordMapper.findByCollegeScore(examId, collegeId, courseCode);
    }

    /**
     * 获取班级分数
     *
     * @param examId
     * @param collegeId
     * @param classNo
     * @param courseCode
     * @return
     */
    @Override
//    @Cacheable(value = "class_score_cache", key = "#examId + '-' + #collegeId + '-' + #classNo + '-' + #courseCode", unless = "#result == null")
    public SynthesisBean findByClassScore(Long examId, Long collegeId, String classNo, String courseCode) {
        return teExamRecordMapper.findByClassScore(examId, collegeId, classNo, courseCode);
    }

    /**
     * 获取分数比自己低的人数
     *
     * @param examId
     * @param collegeId
     * @param examRecordId
     * @param courseCode
     * @return
     */
    @Override
//    @Cacheable(value = "low_score_cache", key = "#examId + '-' + #collegeId + '-' + #examRecordId + '-' + #courseCode", unless = "#result == null")
    public Integer getLowScoreByMe(Long examId, Long collegeId, Long examRecordId, String courseCode) {
        return teExamRecordMapper.getLowScoreByMe(examId, collegeId, examRecordId, courseCode);
    }
}
