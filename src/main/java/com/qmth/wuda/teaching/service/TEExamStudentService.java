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

    void deleteAll();

    /**
     * 根据考生考号查询
     *
     * @param identity
     * @return
     */
    ExamStudentDto findByStudentNo(String identity);

    /**
     * 获取实考人数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param miss
     * @return
     */
    Integer findByActualCount(Long schoolId, Long examId, Long collegeId, Integer miss);

    /**
     * 根据学生id和考试id获取考试科目
     *
     * @param studentId
     * @param examId
     * @return
     */
    List<ExamCourseDto> findByStudentIdAndExamId(Long studentId, Long examId);
}
