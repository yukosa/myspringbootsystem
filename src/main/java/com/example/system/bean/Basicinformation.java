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
    private String userName;
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
}
