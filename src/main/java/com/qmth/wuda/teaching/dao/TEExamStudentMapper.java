package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.dto.ExamCourseDto;
import com.qmth.wuda.teaching.dto.ExamStudentDto;
import com.qmth.wuda.teaching.entity.TEExamStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考生信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEExamStudentMapper extends BaseMapper<TEExamStudent> {

    /**
     * 根据考试id和学生id和科目编码删除考生
     *
     * @param examId
     * @param studentCode
     * @param examNumber
     * @param courseCode
     */
    void deleteAll(@Param("examId") Long examId, @Param("studentCode") String studentCode, @Param("examNumber") String examNumber, @Param("courseCode") String courseCode);

    /**
     * 根据考生考号查询
     *
     * @param id
     * @return
     */
    ExamStudentDto findById(@Param("id") Long id);

    /**
     * 获取实考人数
     *
     * @param examId
     * @param collegeId
     * @param miss
     * @return
     */
    Integer findByActualCount(@Param("examId") Long examId, @Param("collegeId") Long collegeId, @Param("miss") Integer miss);

    /**
     * 根据学生id获取考试科目
     *
     * @param studentId
     * @return
     */
    List<ExamCourseDto> findByStudentId(@Param("studentId") Long studentId);
}
