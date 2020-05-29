package com.shen.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: shenge
 * @Date: 2020-05-10 11:02
 */

@Data
public class DataExcel {

    //要么用名字匹配，要么用index匹配，不建议两个一起用。
    @ExcelProperty(index = 0)
    private Integer id;

    @ExcelProperty("学生姓名")
    private String name;
}
