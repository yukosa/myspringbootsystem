package com.example.system.serviceImpl;

import com.example.system.bean.Dailyinfo;
import com.example.system.bean.User;
import com.example.system.mapper.DailyinfoMapper;
import com.example.system.mapper.UserMapper;
import com.example.system.service.DailyinfoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class DailyinfoServiceImpl implements DailyinfoService{
    @Autowired
    DailyinfoMapper dailyinfoMapper;

    @Override
    public Dailyinfo queryById(int id){return dailyinfoMapper.queryById(id);}

    @Override
    public List<Dailyinfo> queryAll(){return dailyinfoMapper.queryAll();}

    @Override
    public Date getMaxDate(){return dailyinfoMapper.getMaxDate();}

    @Override
    public Date getMinDate(){return dailyinfoMapper.getMinDate();}
}
