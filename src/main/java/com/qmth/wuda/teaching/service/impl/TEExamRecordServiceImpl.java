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

    @Override
    public void deleteAll() {
        teExamRecordMapper.deleteAll();
    }

    /**
     * 获取学院分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @return
     */
    @Override
    @Cacheable(value = "college_score_cache", key = "#schoolId + '-' + #examId + '-' + #collegeId + '-' + #courseCode", unless = "#result == null")
    public SynthesisBean findByCollegeScore(Long schoolId, Long examId, Long collegeId, String courseCode) {
        return teExamRecordMapper.findByCollegeScore(schoolId, examId, collegeId, courseCode);
    }

    /**
     * 获取班级分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param classNo
     * @param courseCode
     * @return
     */
    @Override
    @Cacheable(value = "class_score_cache", key = "#schoolId + '-' + #examId + '-' + #collegeId + '-' + #classNo + '-' + #courseCode", unless = "#result == null")
    public SynthesisBean findByClassScore(Long schoolId, Long examId, Long collegeId, String classNo, String courseCode) {
        return teExamRecordMapper.findByClassScore(schoolId, examId, collegeId, classNo, courseCode);
    }

    /**
     * 获取分数比自己低的人数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param examRecordId
     * @param courseCode
     * @return
     */
    @Override
    @Cacheable(value = "low_score_cache", key = "#schoolId + '-' + #examId + '-' + #collegeId + '-' + #examRecordId + '-' + #courseCode", unless = "#result == null")
    public Integer getLowScoreByMe(Long schoolId, Long examId, Long collegeId, Long examRecordId, String courseCode) {
        return teExamRecordMapper.getLowScoreByMe(schoolId, examId, collegeId, examRecordId, courseCode);
    }
}
