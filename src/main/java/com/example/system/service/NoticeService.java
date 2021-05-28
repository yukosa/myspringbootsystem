package com.example.system.service;

import com.example.system.bean.Notice;

import java.util.Map;

public interface NoticeService {
    void saveNotice(Notice notice);
    String getNewNotice();
}
