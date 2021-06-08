package com.example.system.mapper;


import com.example.system.bean.DailyinfoExcel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DailyinfoExcelMapper {
    List<DailyinfoExcel> queryAll();
    List<DailyinfoExcel> queryByDate(String date);
}
