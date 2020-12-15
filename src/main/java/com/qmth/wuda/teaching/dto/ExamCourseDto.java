package com.qmth.wuda.teaching.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 考试科目临时传输对象 dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/4
 */
public class ExamCourseDto implements Serializable {

    @ApiModelProperty(value = "科目名称")
    private String courseName;

    @ApiModelProperty(value = "科目编码")
    private String courseCode;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
