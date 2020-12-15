package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEExam;

/**
 * <p>
 * 考试批次表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEExamService extends IService<TEExam> {

    /**
     * 根据考试id或考试编码删除考试
     *
     * @param examId
     * @param examCode
     */
    void deleteAll(Long examId, String examCode);

    /**
     * 根据考试id或考试编码创建考试
     *
     * @param examId
     * @param examCode
     * @param accessKey
     * @param accessSecret
     * @return
     */
    TEExam saveExam(Long examId, String examCode, String accessKey, String accessSecret);
}
