package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.dto.ExamDto;
import com.qmth.wuda.teaching.entity.TEExam;

import java.util.List;

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
     * @param examName
     * @param examId
     * @param examCode
     * @param accessKey
     * @param accessSecret
     * @return
     */
    TEExam saveExam(String examName, Long examId, String examCode, String accessKey, String accessSecret);

    /**
     * 根据考试名称查询考试
     *
     * @param examName
     * @return
     */
    List<ExamDto> findByExamName(String examName);
}
