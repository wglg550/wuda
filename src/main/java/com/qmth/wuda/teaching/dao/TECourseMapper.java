package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TECourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 科目信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TECourseMapper extends BaseMapper<TECourse> {

    /**
     * 根据学校id和科目代码删除科目
     *
     * @param schoolId
     * @param courseCodes
     * @param paperCode
     */
    void deleteAll(@Param("schoolId") Long schoolId, @Param("courseCodes") Set<String> courseCodes,@Param("paperCode") String paperCode);
}
