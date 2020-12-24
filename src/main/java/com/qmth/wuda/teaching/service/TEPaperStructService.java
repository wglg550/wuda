package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEPaperStruct;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 试卷结构题型说明 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEPaperStructService extends IService<TEPaperStruct> {

    /**
     * 根据试卷id删除题目
     *
     * @param paperId
     */
    void deleteAll(Long paperId);

    /**
     * 根据试卷id查询题目
     *
     * @param paperId
     * @return
     */
    List<TEPaperStruct> findByPaperId(Long paperId);

    /**
     * 根据维度查找总分
     *
     * @param dimensions
     * @param moduleCode
     * @return
     */
    BigDecimal paperStructSumScoreByDimension(List<String> dimensions, String moduleCode);

    /**
     * 查找学生答题维度
     *
     * @param examId
     * @param studentCode
     * @param courseCode
     * @param moduleCode
     * @return
     */
    List<String> findStudentDimension(Long examId, String studentCode, String courseCode, String moduleCode);
}
