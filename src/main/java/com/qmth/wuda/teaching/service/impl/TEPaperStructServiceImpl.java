package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEPaperStructMapper;
import com.qmth.wuda.teaching.entity.TEPaperStruct;
import com.qmth.wuda.teaching.service.TEPaperStructService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
//    @CacheEvict(value = "paper_struct_cache", key = "#paperId")
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
//    @Cacheable(value = "paper_struct_cache", key = "#paperId", unless = "#result == null")
    public List<TEPaperStruct> findByPaperId(Long paperId) {
        return tePaperStructMapper.findByPaperId(paperId);
    }

    /**
     * 根据维度查找总分
     *
     * @param dimensions
     * @param moduleCode
     * @return
     */
    @Override
    public BigDecimal paperStructSumScoreByDimension(List<String> dimensions, String moduleCode) {
        return tePaperStructMapper.paperStructSumScoreByDimension(dimensions, moduleCode);
    }

    /**
     * 查找学生答题维度
     *
     * @param examId
     * @param studentCode
     * @param courseCode
     * @param moduleCode
     * @return
     */
    @Override
    public List<String> findStudentDimension(Long examId, String studentCode, String courseCode, String moduleCode) {
        return tePaperStructMapper.findStudentDimension(examId, studentCode, courseCode, moduleCode);
    }
}