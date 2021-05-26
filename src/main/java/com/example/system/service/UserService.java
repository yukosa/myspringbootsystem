package com.example.system.service;

import com.example.system.bean.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);
    String getPwdById(int id);

    //登录时信息核对
    User loginIn(String userName,String pwd);

    //根据ID查询用户信息
    User queryUserById(String id);

    //插入新的用户
    int addUser(User user);

    //删除用户
    int dropUser(int id);

    //修改用户
    int modifyUser(User user);

    //查询所有用户
    List<User> queryAllUser();
}
