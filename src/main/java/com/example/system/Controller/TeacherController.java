package com.example.system.Controller;


import com.example.system.service.DailyinfoService;
import com.example.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

@Controller
public class TeacherController {
    @Autowired
    DailyinfoService dailyinfoService;

    @Autowired
    UserService userService;

    @RequestMapping("/user/teacher")
    public String gloabalfresh(Model model,HttpServletRequest request){

        model.addAttribute("a",dailyinfoService.getTodayNum());
        model.addAttribute("b", userService.getAllUserNum());

        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("identity");
        // 没有登录，返回登录页面
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }

        String loginIdentity = (String) obj;                    // 强制转换成 String

        // 如果是教师登录就返回教师页面
        if (loginIdentity.equals("1")||loginIdentity.equals("2")) {
            return "teacher/teaindex";
        }
        //        返回导航界面
        return "redirect:/index";

    }
}
