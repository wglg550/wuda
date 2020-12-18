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
     * @param moduleId
     */
    @Override
//    @CacheEvict(value = "level_cache", key = "#moduleId")
    public void deleteAll(Long moduleId) {
        tbLevelMapper.deleteAll(moduleId);
    }

    /**
     * 根据模块id查询所有等级
     *
     * @param moduleId
     * @return
     */
    @Override
//    @Cacheable(value = "level_cache", key = "#moduleId", condition = "#result != null")
    public List<TBLevel> findByModuleId(Long moduleId) {
        QueryWrapper<TBLevel> tbLevelQueryWrapper = new QueryWrapper<>();
        tbLevelQueryWrapper.lambda().eq(TBLevel::getModuleId, moduleId);
        return this.list(tbLevelQueryWrapper);
    }
}
