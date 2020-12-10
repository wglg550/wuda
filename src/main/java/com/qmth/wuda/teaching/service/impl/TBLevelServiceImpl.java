package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TBLevelMapper;
import com.qmth.wuda.teaching.entity.TBLevel;
import com.qmth.wuda.teaching.service.TBLevelService;
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

    @Override
    public void deleteAll() {
        tbLevelMapper.deleteAll();
    }

    @Override
    @Cacheable(value = "level_cache", key = "methodName")
    public List<TBLevel> findAll() {
        return this.list();
    }
}
