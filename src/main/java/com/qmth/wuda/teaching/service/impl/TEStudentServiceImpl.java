package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEStudentMapper;
import com.qmth.wuda.teaching.entity.TEStudent;
import com.qmth.wuda.teaching.service.TEStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEStudentServiceImpl extends ServiceImpl<TEStudentMapper, TEStudent> implements TEStudentService {

    @Resource
    TEStudentMapper teStudentMapper;

    /**
     * 根据学校id和学号删除学生
     *
     * @param schoolId
     * @param studentCodes
     */
    @Override
    public void deleteAll(Long schoolId, Set<String> studentCodes) {
        teStudentMapper.deleteAll(schoolId, studentCodes);
    }

    /**
     * 根据学号获取学生信息
     *
     * @param studentCode
     * @return
     */
    @Override
    public TEStudent findByStudentCode(String studentCode) {
        QueryWrapper<TEStudent> teStudentQueryWrapper = new QueryWrapper<>();
        teStudentQueryWrapper.lambda().eq(TEStudent::getStudentCode, studentCode);
        return this.getOne(teStudentQueryWrapper);
    }
}
