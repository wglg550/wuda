package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEAnswer;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEAnswerService extends IService<TEAnswer> {

    void deleteAll();

    /**
     * 根据考试记录id查找答题记录
     *
     * @param examRecordId
     * @return
     */
    List<TEAnswer> findByExamRecordId(Long examRecordId);
}
