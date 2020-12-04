package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TEExamStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 考生信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEExamStudentService extends IService<TEExamStudent> {

    void deleteAll();
}
