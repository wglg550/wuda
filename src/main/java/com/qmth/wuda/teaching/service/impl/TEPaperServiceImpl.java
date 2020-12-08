package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEPaperMapper;
import com.qmth.wuda.teaching.entity.TEPaper;
import com.qmth.wuda.teaching.service.TEPaperService;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 根据考试id和科目编码查询试卷
     *
     * @param examId
     * @param courseCode
     * @return
     */
    @Override
    @Cacheable(value = "paper_cache", key = "#examId + '-' + #courseCode", unless = "#result == null")
    public TEPaper findByExamIdAndCourseCode(Long examId, String courseCode) {
        return tePaperMapper.findByExamIdAndCourseCode(examId, courseCode);
    }
}
