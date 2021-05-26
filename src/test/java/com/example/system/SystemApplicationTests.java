package com.example.system;

import com.example.system.bean.User;
import com.example.system.mapper.UserMapper;
import com.example.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SystemApplicationTests {
    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
        User user1 = userService.getUserById(1827405075);
        System.out.println("???????"+user1.getPwd());
        System.out.println("???????"+userService.getPwdById(1827405075));
    }

}
