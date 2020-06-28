package com.oyyo.eduservice.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 课程分类vo
 */
@Data
public class SubjectExcelVO {

    @ExcelProperty(index = 0)
    private String oneLevelSubjectName;

    @ExcelProperty(index = 1)
    private String twoLevelSubjectName;
}
