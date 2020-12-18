package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEExamStudentMapper;
import com.qmth.wuda.teaching.dto.ExamCourseDto;
import com.qmth.wuda.teaching.dto.ExamStudentDto;
import com.qmth.wuda.teaching.entity.TEExamStudent;
import com.qmth.wuda.teaching.service.TEExamStudentService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 考生信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEExamStudentServiceImpl extends ServiceImpl<TEExamStudentMapper, TEExamStudent> implements TEExamStudentService {

    @Resource
    TEExamStudentMapper teExamStudentMapper;

    /**
     * 根据考试id和学生id和科目编码删除考生
     *
     * @param examId
     * @param studentCode
     * @param examNumber
     * @param courseCode
     */
    @Override
    public void deleteAll(Long examId, String studentCode, String examNumber, String courseCode) {
        teExamStudentMapper.deleteAll(examId, studentCode, examNumber, courseCode);
    }

    /**
     * 根据考生考号查询
     *
     * @param studentCode
     * @return
     */
    @Override
    @Cacheable(value = "exam_student_cache", key = "#studentCode", unless = "#result == null")
    public ExamStudentDto findByStudentNo(String studentCode) {
        return teExamStudentMapper.findByStudentNo(studentCode);
    }

    /**
     * 获取实考人数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param miss
     * @return
     */
    @Override
    @Cacheable(value = "exam_param_cache", key = "#schoolId + '-' + #examId + '-' + #collegeId + '-' + #miss", unless = "#result == null")
    public Integer findByActualCount(Long schoolId, Long examId, Long collegeId, Integer miss) {
        return teExamStudentMapper.findByActualCount(schoolId, examId, collegeId, miss);
    }

    /**
     * 根据学生id获取考试科目
     *
     * @param studentId
     * @return
     */
    @Override
    public List<ExamCourseDto> findByStudentId(Long studentId) {
        return teExamStudentMapper.findByStudentId(studentId);
    }
}
