package com.qmth.wuda.teaching.bean.report.course;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 考生最终信息
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/28
 */
public class StudentReallyInfoBean implements Serializable {

    @ApiModelProperty(value = "考试信息")
    private List<ExamInfoBean> examInfo;

    @ApiModelProperty(value = "考生信息")
    private StudentInfoBean studentInfo;

    public List<ExamInfoBean> getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(List<ExamInfoBean> examInfo) {
        this.examInfo = examInfo;
    }

    public StudentInfoBean getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfoBean studentInfo) {
        this.studentInfo = studentInfo;
    }
}
