package com.example.system.Controller;


import com.example.system.service.DailyinfoService;
import com.example.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

@Controller
public class TeacherController {
    @Autowired
    DailyinfoService dailyinfoService;

    @Autowired
    UserService userService;

    @RequestMapping("/user/teacher")
    public String gloabalfresh(Model model){

        model.addAttribute("a",dailyinfoService.getTodayNum());
        model.addAttribute("b", userService.getAllUserNum());

        return "teacher/teaindex";
    }
}
