package com.example.system.Controller;



import com.example.system.bean.Dailyinfo;
import com.example.system.bean.User;
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
public class dailyinfoController {

    @Autowired
    DailyinfoService dailyinfoService;
    @Autowired
    UserService userService;

    @RequestMapping("/user/dailyinfo/querry")
    public String gloabalfresh(Model model,HttpServletRequest request,
                               @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                               @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){
        if (pageNum == null) {
            pageNum = 1;   //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;    //设置默认每页显示的数据数
        }

        PageHelper.startPage(pageNum, pageSize);
        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            List<Dailyinfo> dailyinfos = dailyinfoService.queryById(loginId);

            System.out.println("分页数据"+dailyinfos);
            PageInfo<Dailyinfo> pageInfo = new PageInfo<Dailyinfo>(dailyinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "student/dailyquerry";
    }

    @RequestMapping("/user/dailyinfo/querry/day/{num}")
    public String querryday(Model model,HttpServletRequest request,@PathVariable("num")int num){



        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            Dailyinfo dailyinfo = dailyinfoService.queryByNum(num);
            model.addAttribute("dailyinfo",dailyinfo);
        } finally {
            PageHelper.clearPage();
        }

        return "student/mydaily";
    }


    @RequestMapping("/user/dailyinfo/write")
    public String dailywrite(Model model,HttpServletRequest request){

        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            Date date = new Date(System.currentTimeMillis());
            model.addAttribute("id", loginId);
            model.addAttribute("date", date);
        } finally {
            PageHelper.clearPage();
        }

        return "student/dailywrite";
    }

    @GetMapping("/user/dailyinfo/write/add")
    public String toAddpage(){
        return "student/dailyquerry";
    }
    @PostMapping("/user/dailyinfo/write/add")
    public String AddInfo(Dailyinfo dailyinfo){
        dailyinfoService.addinfo(dailyinfo);

        return "redirect:/user/dailyinfo/querry";
    }


}