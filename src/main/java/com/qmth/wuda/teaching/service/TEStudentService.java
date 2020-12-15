package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEStudent;

/**
 * <p>
 * 学生信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEStudentService extends IService<TEStudent> {

    void deleteAll();

    /**
     * 根据学号(身份证号)获取学生信息
     *
     * @param studentNo
     * @return
     */
    TEStudent findByStudentNo(String studentNo);
}
