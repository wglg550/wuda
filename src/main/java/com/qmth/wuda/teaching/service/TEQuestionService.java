package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEQuestion;

import java.util.List;

/**
 * <p>
 * 试卷结构题型说明 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEQuestionService extends IService<TEQuestion> {

    void deleteAll();

    /**
     * 根据试卷id查询题目
     *
     * @param paperId
     * @return
     */
    List<TEQuestion> findByPaperId(Long paperId);
}
