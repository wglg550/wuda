package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEPaperStructMapper;
import com.qmth.wuda.teaching.entity.TEPaperStruct;
import com.qmth.wuda.teaching.service.TEPaperStructService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 试卷结构题型说明 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEPaperStructServiceImpl extends ServiceImpl<TEPaperStructMapper, TEPaperStruct> implements TEPaperStructService {

    @Resource
    TEPaperStructMapper tePaperStructMapper;

    /**
     * 根据试卷id删除试卷结构
     *
     * @param paperId
     */
    @Override
    @CacheEvict(value = "paper_struct_cache", key = "#paperId")
    public void deleteAll(Long paperId) {
        tePaperStructMapper.deleteAll(paperId);
    }

    /**
     * 根据试卷id查询试卷结构
     *
     * @param paperId
     * @return
     */
    @Override
    @Cacheable(value = "paper_struct_cache", key = "#paperId", unless = "#result == null")
    public List<TEPaperStruct> findByPaperId(Long paperId) {
        return tePaperStructMapper.findByPaperId(paperId);
    }
}
