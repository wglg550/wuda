package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TEExamRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 考试记录表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEExamRecordService extends IService<TEExamRecord> {

    void deleteAll();
}
