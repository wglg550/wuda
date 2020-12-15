package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qmth.wuda.teaching.base.BaseEntity;
import com.qmth.wuda.teaching.util.UidUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 科目信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_course", description = "科目信息表")
public class TECourse extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考试id")
    @TableField(value = "exam_id")
    private Long examId;

    @ApiModelProperty(value = "科目名称")
    @TableField(value = "course_name")
    private String courseName;

    @ApiModelProperty(value = "科目编码")
    @TableField(value = "course_code")
    private String courseCode;

    public TECourse() {

    }

    public TECourse(Long examId, String courseName, String courseCode) {
        setId(UidUtil.nextId());
        this.examId = examId;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

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
