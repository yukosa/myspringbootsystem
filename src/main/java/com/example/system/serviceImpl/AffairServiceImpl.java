package com.example.system.serviceImpl;

import com.example.system.bean.Affair;
import com.example.system.mapper.AffairMapper;
import com.example.system.mapper.BasicinformationMapper;
import com.example.system.service.AffairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AffairServiceImpl implements AffairService{
    //将DAO注入Service层
    @Autowired
    AffairMapper affairMapper;

    @Override
    public List<Affair> getAffairByStatus(int status){return affairMapper.getAffairByStatus(status);}
    public Affair getAffairByUid(int uid){return affairMapper.getAffairByUid(uid);}
    //根据ID查询用户信息
    public List<Affair> selectUserById(int id) {return affairMapper.selectUserById(id);}

    //插入新的用户信息
    public int insertUser(Affair info){return affairMapper.insertUser(info);}

    //删除用户
    public int deleteUser(int id){return affairMapper.deleteUser(id);}

    //修改用户信息info
    public int updateUser(Affair info){return affairMapper.updateUser(info);}

    //    List
    //查询所有用户信息
    public List<Affair> getAllUser(){return affairMapper.getAllUser();}
}
