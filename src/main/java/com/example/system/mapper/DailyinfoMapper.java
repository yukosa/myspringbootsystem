package com.example.system.mapper;

import com.example.system.bean.Dailyinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface DailyinfoMapper {

    Dailyinfo queryById(int id);
    List<Dailyinfo> queryAll();
    Date getMaxDate();
    Date getMinDate();

}
