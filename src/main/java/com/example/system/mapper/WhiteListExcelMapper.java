package com.example.system.mapper;

import com.example.system.bean.WhiteListExcel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WhiteListExcelMapper {
    List<WhiteListExcel> getWhiteList();
}
