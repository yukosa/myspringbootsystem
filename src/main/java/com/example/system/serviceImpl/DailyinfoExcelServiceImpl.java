package com.example.system.serviceImpl;


import com.example.system.bean.DailyinfoExcel;
import com.example.system.mapper.DailyinfoExcelMapper;
import com.example.system.service.DailyinfoExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyinfoExcelServiceImpl implements DailyinfoExcelService {

    @Autowired
    DailyinfoExcelMapper dailyinfoExcelMapper;

    @Override
    public List<DailyinfoExcel> queryAll() {
        return dailyinfoExcelMapper.queryAll();
    }

    @Override
    public List<DailyinfoExcel> queryByDate(String date) {
        return dailyinfoExcelMapper.queryByDate(date);
    }

}
