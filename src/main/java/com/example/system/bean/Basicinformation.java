package com.example.system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basicinformation {
    private int id;
    private String name;
    private String sex;
    private int age;
    private String idCardNo;
    private int phoneNum;
    private String college;
    private String residence;
    private String campus;
    private String placeOfOrigin;
    private String placeOfDomicile;
    private String currentResidence;
    public void setId(int n){id=n;}
    public int getId(){return id;}
    public String getName(){return name;}
    public String getSex(){return sex;}
    public int getAge(){return age;}
    public String getIdCardNo(){return idCardNo;}
    public int getPhoneNum(){return phoneNum;}
    public String getCollege(){return college;}
    public String getResidence(){return residence;}
    public String getCampus(){return campus;}
    public String getPlaceOfOrigin(){return placeOfOrigin;}
    public String getPlaceOfDomicile(){return placeOfDomicile;}
    public String getCurrentResidence(){return currentResidence;}
}
