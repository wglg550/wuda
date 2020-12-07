package com.qmth.wuda.teaching.dto.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 考生公用 dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/4
 */
public class ExamStudentCommonDto implements Serializable {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "学号")
    private String studentNo;

    @ApiModelProperty(value = "学院")
    private String college;

    @ApiModelProperty(value = "班级")
    private String clazz;

    @ApiModelProperty(value = "考试名称")
    private String examName;

    @ApiModelProperty(value = "科目名称")
    private String subject;

    @ApiModelProperty(value = "时间")
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
