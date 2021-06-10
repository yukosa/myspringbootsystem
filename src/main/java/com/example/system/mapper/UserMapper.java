package com.example.system.mapper;

import com.example.system.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserMapper {
    User getUserById(int id);
    String getPwdById(int id);
    //登录时信息核对
    User getInfo(String name,String password);

    //根据ID查询用户信息
    User selectUserById(String id);

    //根据UserName查询用户身份
    String getIdentityByUsername(String userName);

    //插入新的用户
    int addUser(User userBean);

    //删除用户
    int deleteUser(int id);

    //修改用户
    int updateUser(User userBean);

    //查询所有用户
    List<User> getAllUser();

}
