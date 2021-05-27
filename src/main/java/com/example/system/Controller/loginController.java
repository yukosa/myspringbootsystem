package com.example.system.Controller;


import com.example.system.bean.User;
import com.example.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class loginController {

    @Autowired
    UserService userService;
    @PostMapping("/user/login")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password, Model model, HttpSession session){
//        System.out.println("testsasd asdasdssssssssssss");
        User user = userService.getUserById(Integer.parseInt(username));
        if(!StringUtils.isEmpty(username)&&user.getPwd().equals(password)){
            session.setAttribute("username",username);
            session.setAttribute("identity",user.getIdentity());
            return "redirect:/index";
//            if(user.getIdentity().equals("0"))
//                return "redirect:/main.html";
//            else if(user.getIdentity().equals("1"))
//                return "redirect:/user/student";
//            else return "redirect:/main.html";
        }
        else{
            model.addAttribute("msg","用户名或密码错误！");
            return "login";
        }
    }
}