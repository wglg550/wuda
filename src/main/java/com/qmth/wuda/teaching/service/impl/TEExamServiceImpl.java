package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.config.DictionaryConfig;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dao.TEExamMapper;
import com.qmth.wuda.teaching.entity.TEExam;
import com.qmth.wuda.teaching.service.TEExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 考试批次表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEExamServiceImpl extends ServiceImpl<TEExamMapper, TEExam> implements TEExamService {
    private final static Logger log = LoggerFactory.getLogger(TEExamServiceImpl.class);

    @Resource
    TEExamMapper teExamMapper;

    @Resource
    DictionaryConfig dictionaryConfig;

    /**
     * 根据考试id或考试编码删除考试
     *
     * @param examId
     * @param examCode
     */
    @Override
    public void deleteAll(Long examId, String examCode) {
        teExamMapper.deleteAll(examId, examCode);
    }

    /**
     * 根据考试id或考试编码创建考试
     *
     * @param examId
     * @param examCode
     * @param accessKey
     * @param accessSecret
     * @return
     */
    @Override
    @Transactional
    public TEExam saveExam(Long examId, String examCode, String accessKey, String accessSecret) {
        TEExam teExam = null;
        if (Objects.nonNull(examId)) {
            teExam = this.getById(examId);
        } else {
            QueryWrapper<TEExam> teExamQueryWrapper = new QueryWrapper<>();
            teExamQueryWrapper.lambda().eq(TEExam::getAccessKey, accessKey)
                    .eq(TEExam::getAccessSecret, accessSecret)
                    .eq(TEExam::getCode, examCode);
            teExam = this.getOne(teExamQueryWrapper);
        }
        if (Objects.isNull(teExam)) {
            if (Objects.nonNull(examId)) {
                teExam = new TEExam(examId, dictionaryConfig.sysDomain().getExamName(), Objects.isNull(examCode) ? SystemConstant.uuidString() : examCode, accessKey, accessSecret);
            } else {
                teExam = new TEExam(dictionaryConfig.sysDomain().getExamName(), Objects.isNull(examCode) ? SystemConstant.uuidString() : examCode, accessKey, accessSecret);
            }
        } else {
            if (Objects.nonNull(examCode)) {
                teExam.setCode(examCode);
            }
        }
        this.saveOrUpdate(teExam);
        return teExam;
    }
}
