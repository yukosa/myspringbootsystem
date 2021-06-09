package com.example.system.mapper;


import com.example.system.bean.Affair;
import com.example.system.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AffairMapper {
    Affair getAffairByUid(int uid);
    //根据ID查询用户信息
    List<Affair> selectUserById(int id);

    //插入新的用户信息
    int insertUser(Affair info);

    //删除用户
    int deleteUser(int id);

    //修改用户信息info
    int updateUser(Affair info);

    //    List
    //查询所有用户信息
    List<Affair> getAllUser();
}
