package com.qmth.wuda.teaching.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    @ApiModelProperty(value = "是否缺考，0：否，1：是")
    private Integer miss;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考生id")
    private Long examStudentId;

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

    public Integer getMiss() {
        return miss;
    }

    public void setMiss(Integer miss) {
        this.miss = miss;
    }

    public Long getExamStudentId() {
        return examStudentId;
    }

    public void setExamStudentId(Long examStudentId) {
        this.examStudentId = examStudentId;
    }
}
