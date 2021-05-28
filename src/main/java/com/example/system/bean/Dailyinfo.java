package com.example.system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dailyinfo {
    private int num;
    private int id;
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
    private Byte returned;
    public int getNum(){return num ;};
    public int getId(){return id ;};
    public Date getFillingDate(){return fillingDate;}
    public Byte getFill(){return fill;}
    public double getTemOfAm(){return temOfAm;}
    public double getTemOfPm(){return temOfPm;}
    public String getHealth(){return health;}
    public String getPosition(){return position;}
    public String getAddress(){return address;}
    public Byte getMIsolation(){return mIsolation ;}
    public Byte getOutside(){return outside ;}
    public Byte getTouchInfected(){return touchInfected;}
    public Byte getCrossed(){return crossed;}
    public Byte getTravelHRA(){return travelHRA;}
    public Byte getTouchHRA(){return touchHRA;}
    public Byte getReturned(){return returned;}
}