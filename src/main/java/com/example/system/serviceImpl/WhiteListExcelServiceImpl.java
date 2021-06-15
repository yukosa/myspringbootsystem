package com.example.system.serviceImpl;

import com.example.system.bean.WhiteListExcel;
import com.example.system.mapper.WhiteListExcelMapper;
import com.example.system.service.WhiteListExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WhiteListExcelServiceImpl implements WhiteListExcelService {
    @Autowired
    WhiteListExcelMapper whiteListExcelMapper;
    @Override
    public List<WhiteListExcel> getWhiteList() {
        return whiteListExcelMapper.getWhiteList();
    }
}
