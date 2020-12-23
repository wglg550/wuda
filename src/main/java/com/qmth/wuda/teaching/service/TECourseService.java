package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TECourse;

import java.util.Set;

/**
 * <p>
 * 科目信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TECourseService extends IService<TECourse> {

    /**
     * 根据学校id和科目代码删除科目
     *
     * @param schoolId
     * @param courseCodes
     * @param paperCode
     */
    void deleteAll(Long schoolId, Set<String> courseCodes, String paperCode);

    /**
     * 根据科目编码count
     *
     * @param courseCode
     * @return
     */
    int countByCourseCode(String courseCode);
}
