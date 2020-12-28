package com.qmth.wuda.teaching.bean.report.course;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 学生信息
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/28
 */
public class StudentInfoBean implements Serializable {

    @ApiModelProperty(value = "考生名称")
    private String name;

    @ApiModelProperty(value = "学校名称")
    private String school;

    @ApiModelProperty(value = "学校编码")
    private String studentCode;

    public StudentInfoBean() {

    }

    public StudentInfoBean(String name, String school, String studentCode) {
        this.name = name;
        this.school = school;
        this.studentCode = studentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
}
