package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TBSchoolCollege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学院信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TBSchoolCollegeMapper extends BaseMapper<TBSchoolCollege> {

    /**
     * 根据学校id删除学院
     *
     * @param schoolId
     */
    void deleteAll(@Param("schoolId") Long schoolId);
}
