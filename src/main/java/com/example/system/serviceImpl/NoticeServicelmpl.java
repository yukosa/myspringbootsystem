package com.example.system.serviceImpl;

import com.example.system.bean.Notice;
import com.example.system.bean.User;
import com.example.system.mapper.NoticeMapper;
import com.example.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NoticeServicelmpl implements NoticeMapper{
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public void saveNotice(Notice notice){
        noticeMapper.saveNotice(notice);
    }
    //    @Autowired
//    private UserMapper userMapper;
    @Override
    public String getNewNotice(){
        return noticeMapper.getNewNotice();
    }
}
