package com.example.system.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class WhiteListExcel extends BaseRowModel {
    @ExcelProperty(value = "学工号", index = 0)
    int id;
    @ExcelProperty(value = "姓名", index = 1)
    String name;
    @ExcelProperty(value = "性别", index = 2)
    String sex;
    @ExcelProperty(value = "年龄", index = 3)
    int age;
    @ExcelProperty(value = "身份证号", index = 4)
    String idCardNo;
    @ExcelProperty(value = "电话", index = 5)
    int phoneNum;
    @ExcelProperty(value = "学院", index = 6)
    String college;
    @ExcelProperty(value = "住址", index = 7)
    String residence;

}
