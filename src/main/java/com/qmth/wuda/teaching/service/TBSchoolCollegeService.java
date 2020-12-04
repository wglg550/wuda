package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TBSchoolCollege;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学院信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBSchoolCollegeService extends IService<TBSchoolCollege> {

    void deleteAll();
}
