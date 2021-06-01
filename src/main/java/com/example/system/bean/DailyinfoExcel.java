package com.example.system.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;

@Data
public class DailyinfoExcel extends BaseRowModel {
    @ExcelProperty(value = "学工号",index=0)
    private Integer id;
    @ExcelProperty(value = "填报日期",index=1)
    private Date fillingDate;
    @ExcelProperty(value = "是否打卡",index=2)
    private Byte fill;
    @ExcelProperty(value = "上午体温",index=3)
    private double temOfAm;
    @ExcelProperty(value = "下午体温",index=4)
    private double temOfPm;
    @ExcelProperty(value = "健康状况",index=5)
    private String health;
    @ExcelProperty(value = "现处位置",index=6)
    private String position;
    @ExcelProperty(value = "具体地址",index=7)
    private String address;
    @ExcelProperty(value = "是否医学隔离",index=8)
    private Byte mIsolation;
    @ExcelProperty(value = "是否外出",index=9)
    private Byte outside;
    @ExcelProperty(value = "是否与确诊/无症状感染者接触",index=10)
    private Byte touchInfected;
    @ExcelProperty(value = "是否与确诊/无症状感染者行程轨迹交叉",index=11)
    private Byte crossed;
    @ExcelProperty(value = "是否中高风险地区旅居史",index=12)
    private Byte travelHRA;
    @ExcelProperty(value = "是否中高风险地区人员接触",index=13)
    private Byte touchHRA;
    @ExcelProperty(value = "是否返校",index=15)
    private Byte returned;
}