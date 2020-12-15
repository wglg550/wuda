package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEExamMapper;
import com.qmth.wuda.teaching.entity.TBSchool;
import com.qmth.wuda.teaching.entity.TBSchoolCollege;
import com.qmth.wuda.teaching.entity.TEExam;
import com.qmth.wuda.teaching.service.TBSchoolCollegeService;
import com.qmth.wuda.teaching.service.TBSchoolService;
import com.qmth.wuda.teaching.service.TEExamService;
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

    @Resource
    TEExamMapper teExamMapper;

    @Resource
    TBSchoolService tbSchoolService;

    @Resource
    TBSchoolCollegeService tbSchoolCollegeService;

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
     * @param collegeName
     * @param accessKey
     * @param accessSecret
     */
    @Override
    @Transactional
    public void saveExam(Long examId, String examCode, String collegeName, String accessKey, String accessSecret) {
        QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
        tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
        TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);
        TEExam teExam = null;
        if (Objects.nonNull(examId)) {
            teExam = this.getById(examId);
        } else {
            QueryWrapper<TEExam> teExamQueryWrapper = new QueryWrapper<>();
            teExamQueryWrapper.lambda().eq(TEExam::getSchoolId, tbSchool.getId())
                    .eq(TEExam::getCode, examCode);
            teExam = this.getOne(teExamQueryWrapper);
        }
        if (Objects.isNull(teExam)) {
            teExam = new TEExam(tbSchool.getId(), "武汉大学考试", examCode);
        }
        QueryWrapper<TBSchoolCollege> tbSchoolCollegeQueryWrapper = new QueryWrapper<>();
        tbSchoolCollegeQueryWrapper.lambda().eq(TBSchoolCollege::getSchoolId, tbSchool.getId())
                .eq(TBSchoolCollege::getName, collegeName);
        TBSchoolCollege tbSchoolCollege = tbSchoolCollegeService.getOne(tbSchoolCollegeQueryWrapper);
        if (Objects.isNull(tbSchoolCollege)) {
            tbSchoolCollege = new TBSchoolCollege(tbSchool.getId(), collegeName, collegeName, accessKey, accessSecret);
        } else {
            tbSchoolCollege.setName(collegeName);
            tbSchoolCollege.setCode(collegeName);
            tbSchoolCollege.setAccessKey(accessKey);
            tbSchoolCollege.setAccessSecret(accessSecret);
        }
        teExam.setCollegeId(tbSchoolCollege.getId());
        tbSchoolCollegeService.saveOrUpdate(tbSchoolCollege);
        this.saveOrUpdate(teExam);
    }
}
