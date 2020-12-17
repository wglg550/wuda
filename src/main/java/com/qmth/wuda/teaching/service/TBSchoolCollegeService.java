package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBSchoolCollege;

/**
 * <p>
 * 学院信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBSchoolCollegeService extends IService<TBSchoolCollege> {

    /**
     * 根据学校id删除学院
     *
     * @param schoolId
     */
    void deleteAll(Long schoolId);
}
