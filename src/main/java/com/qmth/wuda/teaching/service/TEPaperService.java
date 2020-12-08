package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEPaper;

/**
 * <p>
 * 试卷信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEPaperService extends IService<TEPaper> {

    void deleteAll();

    /**
     * 根据考试id和科目编码查询试卷
     *
     * @param examId
     * @param courseCode
     * @return
     */
    TEPaper findByExamIdAndCourseCode(Long examId, String courseCode);
}
