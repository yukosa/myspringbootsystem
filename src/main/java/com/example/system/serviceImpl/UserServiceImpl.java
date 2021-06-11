package com.example.system.serviceImpl;

import com.example.system.bean.User;
import com.example.system.mapper.UserMapper;
import com.example.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

//    UserMapper
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int id){
        return userMapper.getUserById(id);
    }
//    @Autowired
//    private UserMapper userMapper;
    @Override
    public String getPwdById(int id){
        return userMapper.getPwdById(id);
    }

    @Override
    public String getIdentityByUsername(String userName){
        return userMapper.getIdentityByUsername(userName);
    }

    @Override
    public User loginIn(String name, String password) {
        return userMapper.getInfo(name,password);
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    @Override
    public User queryUserById(String id) {
        return userMapper.selectUserById(id);
    }


    /**
     * 新增用户
     * @param userBean
     * @return
     */
    @Override
    public int addUser(User userBean) {
        int aFlag = userMapper.addUser(userBean);
        return aFlag;
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    @Override
    public int dropUser(int id) {
        int dFlag = userMapper.deleteUser(id);
        return dFlag;
    }


    /**
     * 修改用户信息
     * remark：实际上还是根据用户ID修改用户信息
     * @param user
     * @return
     */
    @Override
    public int modifyUser(User user) {
        int mFlag = userMapper.updateUser(user);
        return mFlag;
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    //查询需要填报的总用户数量
    public int getAllUserNum(){ return userMapper.getAllUserNum();};
}
