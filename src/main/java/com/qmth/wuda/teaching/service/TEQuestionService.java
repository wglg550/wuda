package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TEQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
