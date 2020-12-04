package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TEExam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 考试批次表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEExamService extends IService<TEExam> {

    void deleteAll();
}
