package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TBDimensionMapper;
import com.qmth.wuda.teaching.entity.TBDimension;
import com.qmth.wuda.teaching.service.TBDimensionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 维度信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBDimensionServiceImpl extends ServiceImpl<TBDimensionMapper, TBDimension> implements TBDimensionService {

    @Resource
    TBDimensionMapper tbDimensionMapper;

    @Override
    public void deleteAll() {
        tbDimensionMapper.deleteAll();
    }
}
