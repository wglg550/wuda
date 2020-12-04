package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TEPaper;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
