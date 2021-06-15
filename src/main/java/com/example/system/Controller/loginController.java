package com.example.system.Controller;


import com.example.system.bean.User;
import com.example.system.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
public class loginController {

    @Autowired
    UserService userService;
    @PostMapping("/user/login")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("pass")String pass,Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserById(Integer.parseInt(username));
        if(user!=null&&!StringUtils.isEmpty(username)&&user.getPwd().equals(password)){
            if(pass!=null&&!pass.equals("nopass")){

                Cookie cookie_username = new Cookie("cookie_username",username);
                // 设置cookie的持久化时间，30天
                cookie_username.setMaxAge(30 * 24 * 60 * 60);
                // 设置为当前项目下都携带这个cookie
                cookie_username.setPath("/");
                // 向客户端发送cookie
                response.addCookie(cookie_username);
                System.out.println("成功设置cookie");
            }
            session.setAttribute("username",username);
            session.setAttribute("identity",user.getIdentity());
//            Coolie
            return "redirect:/index";
        }
        else{
            model.addAttribute("msg","用户名或密码错误！");
            return "login";
        }
    }
    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute("username");
        session.removeAttribute("identity");
        // 保存cookie，实现自动登录
        Cookie cookie_username = new Cookie("cookie_username", "");
        // 设置cookie的持久化时间，0
        cookie_username.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath("/");
        // 向客户端发送cookie
        response.addCookie(cookie_username);
        return "login";
    }

    @RequestMapping("/user/passwd")
    public String passwd( Model model,HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            User user=userService.getUserById(loginId);
            String name=user.getUserName();
            model.addAttribute("id", loginId);
            model.addAttribute("name", name);
            return "editpasswd";
        } finally {
            PageHelper.clearPage();
        }
    }
    @RequestMapping("/user/passwd/edit")
    public String editpasswd(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                          @RequestParam("pwd") String pwd,
                          @RequestParam("npwd")String npwd,
                          Model model,HttpServletRequest request) {
        int flag = 0;
        User user=userService.getUserById(id);
        if(user.getPwd().equals(pwd)){
            flag=1;
            user.setPwd(npwd);
        }
        userService.modifyUser(user);
        if(flag == 1){
            model.addAttribute("msg","修改密码成功");
        }else {
            model.addAttribute("msg","修改密码失败");
        }
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "editpasswd";
    }


}

