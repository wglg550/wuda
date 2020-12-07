package com.qmth.wuda.teaching.bean.report;

import com.qmth.wuda.teaching.dto.common.ExamStudentCommonDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 考生 bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/4
 */
public class ExamStudentBean extends ExamStudentCommonDto implements Serializable {

    @ApiModelProperty(value = "等级")
    private String level;

    @ApiModelProperty(value = "等级百分比")
    private List<LevelBean> levels;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<LevelBean> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelBean> levels) {
        this.levels = levels;
    }
}
