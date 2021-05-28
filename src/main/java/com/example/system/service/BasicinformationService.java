package com.example.system.service;

import com.example.system.bean.Basicinformation;

import java.util.List;

public interface BasicinformationService {
    //根据ID查询用户信息
    Basicinformation selectUserById(String id);

    //插入新的用户信息
    int insertUser(Basicinformation info);

    //删除用户
    int deleteUser(int id);

    //修改用户信息info
    int updateUser(Basicinformation info);
//    List
    //查询所?有用户信息
    List<Basicinformation> getAllUser();
}
