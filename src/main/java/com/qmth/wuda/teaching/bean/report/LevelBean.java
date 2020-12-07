package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 等级 bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/4
 */
public class LevelBean implements Serializable {

    @ApiModelProperty(value = "等级")
    private String level;

    @ApiModelProperty(value = "分数比")
    private List<Integer> grade;

    public LevelBean() {

    }

    public LevelBean(String level, List<Integer> grade) {
        this.level = level;
        this.grade = grade;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Integer> getGrade() {
        return grade;
    }

    public void setGrade(List<Integer> grade) {
        this.grade = grade;
    }
}
