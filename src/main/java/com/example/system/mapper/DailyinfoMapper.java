package com.example.system.mapper;

import com.example.system.bean.Basicinformation;
import com.example.system.bean.Dailyinfo;
import com.example.system.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface DailyinfoMapper {

    List<Dailyinfo> queryById(int id);

    List<Dailyinfo> queryAll();

    Date getMaxDate();

    Date getMinDate();

    Dailyinfo queryByNum(int num);

    int addinfo(Dailyinfo dailyindo);

    List<Basicinformation> getWhiteList();

    int getTodayNum();
}
