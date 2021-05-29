package com.example.system.mapper;


import com.example.system.bean.Basicinformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BasicinformationMapper {
    //根据ID查询用户信息
    Basicinformation selectUserById(int id);

    //插入新的用户信息
    int insertUser(Basicinformation info);

    //删除用户
    int deleteUser(int id);

    //修改用户信息info
    int updateUser(Basicinformation info);

//    List
    //查询所有用户信息
    List<Basicinformation> getAllUser();
}
