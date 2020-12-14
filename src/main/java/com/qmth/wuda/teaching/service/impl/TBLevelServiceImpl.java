package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TBLevelMapper;
import com.qmth.wuda.teaching.entity.TBLevel;
import com.qmth.wuda.teaching.service.TBLevelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 等级信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBLevelServiceImpl extends ServiceImpl<TBLevelMapper, TBLevel> implements TBLevelService {

    @Resource
    TBLevelMapper tbLevelMapper;

    /**
     * 根据模块id删除等级
     *
     * @param schoolId
     */
    @Override
    @CacheEvict(value = "level_cache", key = "#schoolId")
    public void deleteAll(Long schoolId) {
        tbLevelMapper.deleteAll(schoolId);
    }

    /**
     * 根据模块id查询所有等级
     *
     * @param schoolId
     * @return
     */
    @Override
    @Cacheable(value = "level_cache", key = "#schoolId", condition = "#result != null")
    public List<TBLevel> findBySchoolId(Long schoolId) {
        QueryWrapper<TBLevel> tbLevelQueryWrapper = new QueryWrapper<>();
        tbLevelQueryWrapper.lambda().eq(TBLevel::getSchoolId, schoolId);
        return this.list(tbLevelQueryWrapper);
    }
}
