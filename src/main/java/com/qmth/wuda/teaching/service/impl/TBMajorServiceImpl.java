package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TBMajor;
import com.qmth.wuda.teaching.dao.TBMajorMapper;
import com.qmth.wuda.teaching.service.TBMajorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 专业信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBMajorServiceImpl extends ServiceImpl<TBMajorMapper, TBMajor> implements TBMajorService {

    @Resource
    TBMajorMapper tbMajorMapper;

    @Override
    public void deleteAll() {
        tbMajorMapper.deleteAll();
    }
}
