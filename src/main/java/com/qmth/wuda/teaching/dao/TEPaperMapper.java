package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TEPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 试卷信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEPaperMapper extends BaseMapper<TEPaper> {

    /**
     * 根据考试id和试卷code和科目编码删除试卷
     *
     * @param examId
     * @param code
     * @param courseCode
     */
    void deleteAll(@Param("examId") Long examId, @Param("code") String code, @Param("courseCode") String courseCode);

    /**
     * 根据考试id和试卷code和科目编码查询试卷
     *
     * @param examId
     * @param courseCode
     * @return
     */
    TEPaper findByExamIdAndCodeAndCourseCode(@Param("examId") Long examId, @Param("code") String code, @Param("courseCode") String courseCode);
}
