package com.oyyo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Test {

    @ExcelProperty(value = "工作名称",index = 0)
    private String name;
    @ExcelProperty(value = "工作方向",index = 1)
    private String work;
}
