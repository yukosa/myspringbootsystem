package com.example.system.service;

import com.example.system.bean.DailyinfoExcel;

import java.util.List;

public interface DailyinfoExcelService {
    List<DailyinfoExcel> queryAll();
    List<DailyinfoExcel> queryByDate(String date);
}
