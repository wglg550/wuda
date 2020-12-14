package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBLevel;

import java.util.List;

/**
 * <p>
 * 等级信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBLevelService extends IService<TBLevel> {

    /**
     * 根据学校id删除等级
     *
     * @param schoolId
     */
    void deleteAll(Long schoolId);

    /**
     * 根据学校id查询所有等级
     *
     * @param schoolId
     * @return
     */
    List<TBLevel> findBySchoolId(Long schoolId);
}
