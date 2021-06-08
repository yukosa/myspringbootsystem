package com.example.system;

import com.example.system.bean.Dailyinfo;
import com.example.system.bean.User;
import com.example.system.mapper.UserMapper;
import com.example.system.service.DailyinfoService;
import com.example.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SystemApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    DailyinfoService dailyinfoService;

    @Test
    void contextLoads() {
//        List<Dailyinfo> dailyinfos=dailyinfoService.queryAll();
        User user = userService.getUserById(2);
        System.out.println(user);


//        User user1 = userService.getUserById(1827405075);
//        System.out.println("???????"+user1.getPwd());
//        System.out.println("???????"+userService.getPwdById(1827405075));
    }

}
