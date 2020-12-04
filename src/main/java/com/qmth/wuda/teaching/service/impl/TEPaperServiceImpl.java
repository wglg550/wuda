package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TEPaper;
import com.qmth.wuda.teaching.dao.TEPaperMapper;
import com.qmth.wuda.teaching.service.TEPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 试卷信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEPaperServiceImpl extends ServiceImpl<TEPaperMapper, TEPaper> implements TEPaperService {

    @Resource
    TEPaperMapper tePaperMapper;

    @Override
    public void deleteAll() {
        tePaperMapper.deleteAll();
    }
}
