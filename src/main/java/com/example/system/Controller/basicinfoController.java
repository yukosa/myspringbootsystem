package com.example.system.Controller;

import com.example.system.bean.Basicinformation;
import com.example.system.bean.Dailyinfo;
import com.example.system.bean.User;
import com.example.system.service.BasicinformationService;
import com.example.system.service.DailyinfoService;
import com.example.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Controller
public class basicinfoController {

    @Autowired
    BasicinformationService basicinformationService;

    @RequestMapping("/user/basicinformation")
    public String basicwrite(Model model,HttpServletRequest request){
        String identity;
        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            identity= (String) session.getAttribute("identity");
            Basicinformation basicinformation=basicinformationService.selectUserById(loginId);
            if (basicinformation==null){
                //System.out.println("wrong");
                basicinformation = new Basicinformation();
                basicinformation.setId(loginId);
                basicinformationService.insertUser(basicinformation);
            }
            model.addAttribute("basicinformation", basicinformation);
        } finally {
            PageHelper.clearPage();
        }
        if(identity.equals("0")){
            return "student/basicinformation";
        }
        else{
            return "teacher/basicinformation";
        }
    }

    @RequestMapping("/user/basicinformation/modify")
    public String basicmodify(Basicinformation info){

        basicinformationService.updateUser(info);
        return "redirect:/user/basicinformation";
    }


    @RequestMapping("/user/teacher/basicinformation")
    public String basicwrite2(Model model,HttpServletRequest request){
        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            Basicinformation basicinformation=basicinformationService.selectUserById(loginId);
            if (basicinformation==null){
                //System.out.println("wrong");
                basicinformation = new Basicinformation();
                basicinformation.setId(loginId);
                basicinformationService.insertUser(basicinformation);
            }
            model.addAttribute("basicinformation", basicinformation);
        } finally {
            PageHelper.clearPage();
        }

        return "teacher/basicinformation";
    }

    @RequestMapping("/user/teacher/basicinformation/modify")
    public String basicmodify2(Basicinformation info){

        basicinformationService.updateUser(info);
        return "redirect:/user/teacher/basicinformation";
    }
}
