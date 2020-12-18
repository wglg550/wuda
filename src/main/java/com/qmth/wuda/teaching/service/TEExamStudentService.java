package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.dto.ExamCourseDto;
import com.qmth.wuda.teaching.dto.ExamStudentDto;
import com.qmth.wuda.teaching.entity.TEExamStudent;

import java.util.List;

/**
 * <p>
 * 考生信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEExamStudentService extends IService<TEExamStudent> {

    /**
     * 根据考试id和学号和准考证号和科目编码删除考生
     *
     * @param examId
     * @param studentCode
     * @param examNumber
     * @param courseCode
     */
    void deleteAll(Long examId, String studentCode, String examNumber, String courseCode);

    /**
     * 根据考生id查询
     *
     * @param id
     * @return
     */
    ExamStudentDto findById(Long id);

    /**
     * 获取实考人数
     *
     * @param examId
     * @param collegeId
     * @param miss
     * @return
     */
    Integer findByActualCount(Long examId, Long collegeId, Integer miss);

    /**
     * 根据学生id获取考试科目
     *
     * @param studentId
     * @return
     */
    List<ExamCourseDto> findByStudentId(Long studentId);
}