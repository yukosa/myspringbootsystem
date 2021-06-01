package com.example.system.service;

import com.example.system.bean.Dailyinfo;

import java.util.Date;
import java.util.List;

public interface DailyinfoService {
    List<Dailyinfo> queryById(int id);

    List<Dailyinfo> queryAll();

    Date getMaxDate();

    Date getMinDate();

    Dailyinfo queryByNum(int num);

    int addinfo(Dailyinfo dailyindo);
}
