package com.example.system.mapper;

import com.example.system.bean.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public  interface NoticeMapper {
    void saveNotice(Notice notice);
    String getNewNotice();
}
