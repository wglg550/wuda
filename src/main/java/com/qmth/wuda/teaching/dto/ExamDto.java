package com.qmth.wuda.teaching.dto;

/**
 * @Description: 考试临时传输对象dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/15
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ExamDto implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考试id")
    private Long examId;

    @ApiModelProperty(value = "考试名称")
    private String examName;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "考试名称")
    private List<ExamCourseDto> courseList;

    public ExamDto() {

    }

    public ExamDto(Long examId, String examName, Long studentId, String studentName, List<ExamCourseDto> courseList) {
        this.examId = examId;
        this.examName = examName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseList = courseList;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<ExamCourseDto> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<ExamCourseDto> courseList) {
        this.courseList = courseList;
    }
}
