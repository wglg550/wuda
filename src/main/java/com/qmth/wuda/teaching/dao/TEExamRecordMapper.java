package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.bean.report.SynthesisBean;
import com.qmth.wuda.teaching.entity.TEExamRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 考试记录表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEExamRecordMapper extends BaseMapper<TEExamRecord> {

    void deleteAll();

    /**
     * 获取学院分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @return
     */
    SynthesisBean findByCollegeScore(@Param("schoolId") Long schoolId, @Param("examId") Long examId, @Param("collegeId") Long collegeId, @Param("courseCode") String courseCode);

    /**
     * 获取班级分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @return
     */
    SynthesisBean findByClassScore(@Param("schoolId") Long schoolId, @Param("examId") Long examId, @Param("collegeId") Long collegeId, @Param("classNo") String classNo, @Param("courseCode") String courseCode);

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
    Integer getLowScoreByMe(@Param("schoolId") Long schoolId, @Param("examId") Long examId, @Param("collegeId") Long collegeId, @Param("examRecordId") Long examRecordId, @Param("courseCode") String courseCode);
}
