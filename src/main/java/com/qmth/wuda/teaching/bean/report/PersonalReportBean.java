package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 个人报告模版 bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/4
 */
public class PersonalReportBean implements Serializable {

    @ApiModelProperty(value = "考生信息")
    private ExamStudentBean student;

    @ApiModelProperty(value = "学院信息")
    private CollegeBean college;

    public CollegeBean getCollege() {
        return college;
    }

    public void setCollege(CollegeBean college) {
        this.college = college;
    }

    public ExamStudentBean getStudent() {
        return student;
    }

    public void setStudent(ExamStudentBean student) {
        this.student = student;
    }
}
