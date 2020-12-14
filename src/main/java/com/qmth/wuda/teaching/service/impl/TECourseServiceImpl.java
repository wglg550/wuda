package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TECourseMapper;
import com.qmth.wuda.teaching.entity.TECourse;
import com.qmth.wuda.teaching.service.TECourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 * 科目信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TECourseServiceImpl extends ServiceImpl<TECourseMapper, TECourse> implements TECourseService {

    @Resource
    TECourseMapper teCourseMapper;

    /**
     * 根据学校id和科目代码删除科目
     *
     * @param schoolId
     * @param courseCodes
     */
    @Override
    public void deleteAll(Long schoolId, Set<String> courseCodes) {
        teCourseMapper.deleteAll(schoolId, courseCodes);
    }
}
