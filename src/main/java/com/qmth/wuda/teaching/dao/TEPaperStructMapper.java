package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.dto.StudentDimensionDto;
import com.qmth.wuda.teaching.entity.TEPaperStruct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 试卷结构题型说明 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEPaperStructMapper extends BaseMapper<TEPaperStruct> {

    /**
     * 根据试卷id删除题目
     *
     * @param paperId
     */
    void deleteAll(@Param("paperId") Long paperId);

    /**
     * 根据试卷id查询题目
     *
     * @param paperId
     * @return
     */
    List<TEPaperStruct> findByPaperId(@Param("paperId") Long paperId);

    /**
     * 根据维度查找总分
     *
     * @param dimensions
     * @param moduleCode
     * @return
     */
    BigDecimal paperStructSumScoreByDimension(@Param("dimensions") Set<String> dimensions, @Param("moduleCode") String moduleCode);

    /**
     * 查找学生答题维度
     *
     * @param examId
     * @param studentCode
     * @param courseCode
     * @param moduleCode
     * @return
     */
    List<StudentDimensionDto> findStudentDimension(@Param("examId") Long examId, @Param("studentCode") String studentCode, @Param("courseCode") String courseCode, @Param("moduleCode") String moduleCode);
}
