package com.example.system.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

@Controller
public class TeacherController {

    @RequestMapping("/user/teacher")
    public String gloabalfresh(Model model){
        model.addAttribute("a",5);
        model.addAttribute("b", 4);

        return "teacher/teaindex";
    }
}
