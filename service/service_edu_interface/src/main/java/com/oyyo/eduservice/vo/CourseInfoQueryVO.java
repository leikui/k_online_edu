package com.oyyo.eduservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "课程查询对象", description = "课程查询对象封装")
@Data
public class CourseInfoQueryVO {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "讲师id")
    private String teacherId;
    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;
    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;
    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;
    @ApiModelProperty(value = "价格排序")
    private String priceSort;
}
