package com.qmth.wuda.teaching;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.dao.TEExamMapper;
import com.qmth.wuda.teaching.entity.TEExam;
import com.qmth.wuda.teaching.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class TeachingApplicationTests {
    private final static Logger log = LoggerFactory.getLogger(TeachingApplicationTests.class);

    @Resource
    TEExamMapper teExamMapper;

    @Test
    void contextLoads() {
        QueryWrapper<TEExam> teExamQueryWrapper = new QueryWrapper<>();
        teExamQueryWrapper.lambda().eq(TEExam::getCode, "20201204")
                .eq(TEExam::getName, "2020~2021学年上学期期末考试_2");
        List<TEExam> teExamList = teExamMapper.selectList(teExamQueryWrapper);
        log.info("teExamList:{}", JacksonUtil.parseJson(teExamList));
    }
}
