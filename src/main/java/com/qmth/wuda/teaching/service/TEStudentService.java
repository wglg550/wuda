package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEStudent;

import java.util.Set;

/**
 * <p>
 * 学生信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEStudentService extends IService<TEStudent> {

    /**
     * 根据学校id和学号删除学生
     *
     * @param schoolId
     * @param studentCodes
     */
    void deleteAll(Long schoolId, Set<String> studentCodes);

    /**
     * 根据学号获取学生信息
     *
     * @param studentCode
     * @return
     */
    TEStudent findByStudentCode(String studentCode);
}
