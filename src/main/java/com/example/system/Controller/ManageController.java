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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ManageController {
    @RequestMapping("/user/manage")      // 管理页面网址
    public String manage(HttpServletRequest request) {
//        System.out.println("?AAAAAAAAAAAAAAAAAAAAAAAA");
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("identity");
        // 没有登录，返回登录页面
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }

        String loginIdentity = (String) obj;                    // 强制转换成 String

        // 如果是管理员登录就返回管理页面
        if (loginIdentity.equals("2")) {
            return "manage/manindex";
        }
        //        返回导航界面
        return "redirect:/index";
    }

    @Autowired
    UserService userService;
    @Autowired
    DailyinfoService dailyinfoService;

    @RequestMapping("/user/manage/muser")
    public String globalfresh(Model model,
                              @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;   //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;    //设置默认每页显示的数据数
        }
//        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        model.addAttribute("maxDate",simpleDateFormat.format(dailyinfoService.getMaxDate()));
//        model.addAttribute("minDate",simpleDateFormat.format(dailyinfoService.getMinDate()));

        PageHelper.startPage(pageNum, pageSize);
        try {
            List<User> userinfos = userService.getAllUser();
            System.out.println("分页数据"+userinfos);
            PageInfo<User> pageInfo = new PageInfo<User>(userinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
//        System.out.println("当前页是："+ pageNum+"显示条数是："+pageSize);
//        model.addAttribute("userinfos", userinfos);

        return "manage/muser";
//        model.addAttribute("pageInfo", null);
    }
    @GetMapping("/user/manage/muser/add")
    public String toAddpage(){
        return "manage/add";
    }
    @PostMapping("/user/manage/muser/add")
    public String Adduser(User user){
        userService.addUser(user);

        return "redirect:/user/manage/muser";
    }


    @GetMapping("/user/manage/muser/edit/{id}")
    public String toEdit(@PathVariable("id")Integer id,Model model){
//        int idd = Integer.parseInt(id);
        User user =  userService.getUserById(id);
        model.addAttribute("user",user);
        return "manage/edit";
    }
    @PostMapping("/user/manage/muser/edit")
    public String Edituser(User user){
//        userService.addUser(user);
        userService.modifyUser(user);
        return "redirect:/user/manage/muser";
    }

    @GetMapping("/user/manage/muser/delete/{id}")
    public String toDelete(@PathVariable("id")Integer id,Model model){
//        int idd = Integer.parseInt(id);
        userService.dropUser(id);
//        User user =  userService.getUserById(id);
//        model.addAttribute("user",user);
        return "redirect:/user/manage/muser";
    }
}