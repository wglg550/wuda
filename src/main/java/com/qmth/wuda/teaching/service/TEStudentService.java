package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TEStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学生信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEStudentService extends IService<TEStudent> {

    void deleteAll();
}
