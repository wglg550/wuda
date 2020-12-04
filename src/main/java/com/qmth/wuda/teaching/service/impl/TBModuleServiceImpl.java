package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TBModule;
import com.qmth.wuda.teaching.dao.TBModuleMapper;
import com.qmth.wuda.teaching.service.TBModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 模块信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBModuleServiceImpl extends ServiceImpl<TBModuleMapper, TBModule> implements TBModuleService {

    @Resource
    TBModuleMapper tbModuleMapper;

    @Override
    public void deleteAll() {
        tbModuleMapper.deleteAll();
    }
}
