package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 教师考生关系表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_b_teacher_exam_student", description = "教师考生关系表")
public class TBTeacherExamStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "教师id")
    @TableField(value = "teacher_id")
    private Long teacherId;

    @ApiModelProperty(value = "考生主键")
    @TableField(value = "exam_student_id")
    private Long examStudentId;

    @ApiModelProperty(value = "考生唯一身份")
    @TableField(exist = false)
    private String examStudentIdcardNumberAndIdentity;

    public TBTeacherExamStudent() {

    }

    public TBTeacherExamStudent(String examStudentIdcardNumberAndIdentity) {
        this.examStudentIdcardNumberAndIdentity = examStudentIdcardNumberAndIdentity;
    }

    public String getExamStudentIdcardNumberAndIdentity() {
        return examStudentIdcardNumberAndIdentity;
    }

    public void setExamStudentIdcardNumberAndIdentity(String examStudentIdcardNumberAndIdentity) {
        this.examStudentIdcardNumberAndIdentity = examStudentIdcardNumberAndIdentity;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getExamStudentId() {
        return examStudentId;
    }

    public void setExamStudentId(Long examStudentId) {
        this.examStudentId = examStudentId;
    }
}
