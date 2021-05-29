package com.example.system.serviceImpl;


import com.example.system.bean.Basicinformation;
import com.example.system.mapper.BasicinformationMapper;
import com.example.system.service.BasicinformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicinformationServiceImpl implements BasicinformationService {
    //将DAO注入Service层
    @Autowired
    BasicinformationMapper basicinformationMapper;

    //根据ID查询用户信息
    @Override
    public Basicinformation selectUserById(int id) { return basicinformationMapper.selectUserById(id); }

    //插入新的用户信息
    public int insertUser(Basicinformation info){return basicinformationMapper.insertUser(info);}

    //删除用户
    public int deleteUser(int id){return basicinformationMapper.deleteUser(id);}

    //修改用户信息info
    public int updateUser(Basicinformation info){return basicinformationMapper.updateUser(info);}
    //查询所有用户信息
    public List<Basicinformation> getAllUser(){return basicinformationMapper.getAllUser();}
}
