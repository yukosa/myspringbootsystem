package com.example.system.mapper;

import com.example.system.bean.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public  interface NoticeMapper {
    void saveNotice(Notice notice);
    String getNewNotice();
}
