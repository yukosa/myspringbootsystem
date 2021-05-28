package com.example.system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dailyinfo {
    private Integer id;
    private Date fillingDate;
    private Byte fill;
    private double temOfAm;
    private double temOfPm;
    private String health;
    private String position;
    private String address;
    private Byte mIsolation;
    private Byte outside;
    private Byte touchInfected;
    private Byte crossed;
    private Byte travelHRA;
    private Byte touchHRA;
    private Byte rmIsolation;
    private Byte returned;
}