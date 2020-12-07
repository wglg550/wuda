package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.dto.ExamStudentDto;
import com.qmth.wuda.teaching.entity.TEExamStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    void deleteAll();

    /**
     * 根据考生考号查询
     *
     * @param identity
     * @return
     */
    ExamStudentDto findByStudentNo(@Param("identity") String identity);

    /**
     * 获取实考人数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param miss
     * @return
     */
    Integer findByActualCount(@Param("schoolId") Long schoolId, @Param("examId") Long examId, @Param("collegeId") Long collegeId,@Param("miss") Integer miss);
}
