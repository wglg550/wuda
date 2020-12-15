package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: SequenceEntity
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/15
 */
@ApiModel(value = "sequence", description = "序列表")
public class Sequence implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name; //名称

    @TableField("current_value")
    @ApiModelProperty(value = "当前值")
    private Integer currentValue; //当前值

    @TableField("increment")
    @ApiModelProperty(value = "自增值")
    private Integer increment; //自增值

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }
}
