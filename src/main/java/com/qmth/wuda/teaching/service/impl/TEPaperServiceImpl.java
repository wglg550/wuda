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

    /**
     * 根据考试id和试卷code和科目编码删除试卷
     *
     * @param examId
     * @param code
     * @param courseCode
     */
    @Override
    public void deleteAll(Long examId, String code, String courseCode) {
        tePaperMapper.deleteAll(examId, code, courseCode);
    }

    /**
     * 根据考试id和试卷code和科目编码查询试卷
     *
     * @param examId
     * @param code
     * @param courseCode
     * @return
     */
    @Override
    @Cacheable(value = "paper_cache", key = "#examId + '-' + #code + '-' + #courseCode", unless = "#result == null")
    public TEPaper findByExamIdAndCodeAndCourseCode(Long examId, String code, String courseCode) {
        return tePaperMapper.findByExamIdAndCodeAndCourseCode(examId, code, courseCode);
    }
}
