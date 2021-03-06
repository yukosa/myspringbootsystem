package com.example.system.Controller;

import com.example.system.bean.Affair;
import com.example.system.bean.Basicinformation;
import com.example.system.bean.Dailyinfo;
import com.example.system.bean.User;
import com.example.system.service.AffairService;
import com.example.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class affairController {
    @Autowired
    AffairService affairService;
    @Autowired
    UserService userService;

    @RequestMapping("/user/affair")
    public String basicwrite(Model model, HttpServletRequest request){
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
        } finally {
            PageHelper.clearPage();
        }

        return "student/affair";
    }
    @RequestMapping("/user/affair/commit")
    public String affaircommit(Model model,Affair info){

        affairService.insertUser(info);
        return "redirect:/user/affair";
    }

    @RequestMapping("/user/affair/querry")
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
            List<Affair> affairs = affairService.selectUserById(loginId);

//            System.out.println("分页数据"+affairs);
            PageInfo<Affair> pageInfo = new PageInfo<Affair>(affairs, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "student/affairquerry";
    }

    @RequestMapping("/user/affair/querry/myaffair/{uid}")
    public String querryday(Model model,HttpServletRequest request,@PathVariable("uid")int uid){

        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            Affair affair = affairService.getAffairByUid(uid);
            model.addAttribute("affair",affair);
        } finally {
            PageHelper.clearPage();
        }

        return "student/myaffair";
    }

    @RequestMapping("/user/teacher/affair/check")
    public String checkaffair(Model model,HttpServletRequest request,
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
            Object obj1 = session.getAttribute("identity");
            String loginIdentity = (String) obj1;                    // 强制转换成 String
            // 如果权限不足就返回主界面
            if (loginIdentity.equals("0")) {
                return "redirect:/index";
            }

            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            List<Affair> affairs = affairService.getAffairByStatus(0);
//            System.out.println("分页数据"+affairs);
            PageInfo<Affair> pageInfo = new PageInfo<Affair>(affairs, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "teacher/check";
    }
    @RequestMapping("/user/teacher/affair/allow/{uid}")
    public String affairallow(Model model,@PathVariable("uid")int uid,HttpServletRequest request){
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")) {
            return "redirect:/index";
        }
        Affair affair=affairService.getAffairByUid(uid);
        affair.status =1;
        affair.pass=1;
        affairService.updateUser(affair);
        return "redirect:/user/teacher/affair/check";
    }

    @RequestMapping("/user/teacher/affair/checked")
    public String checkedaffair(Model model,HttpServletRequest request,
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
            Object obj1 = session.getAttribute("identity");
            String loginIdentity = (String) obj1;                    // 强制转换成 String
            // 如果权限不足就返回主界面
            if (loginIdentity.equals("0")) {
                return "redirect:/index";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            List<Affair> affairs = affairService.getAffairByStatus(1);
//            System.out.println("分页数据"+affairs);
            PageInfo<Affair> pageInfo = new PageInfo<Affair>(affairs, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "teacher/checked";
    }
    @RequestMapping("/user/teacher/affair/refuse/{uid}")
    public String affairrefuse(Model model,@PathVariable("uid")int uid,HttpServletRequest request){
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")) {
            return "redirect:/index";
        }
        Affair affair=affairService.getAffairByUid(uid);
        affair.status =1;
        affair.pass=0;
        affairService.updateUser(affair);
        return "redirect:/user/teacher/affair/check";
    }
    @RequestMapping("/user/teacher/affair/change/{uid}")
    public String affairchange(Model model,@PathVariable("uid")int uid,HttpServletRequest request){
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")) {
            return "redirect:/index";
        }
        Affair affair=affairService.getAffairByUid(uid);
        if(affair.pass ==1)affair.pass=0;
        else affair.pass=1;
        affairService.updateUser(affair);
        return "redirect:/user/teacher/affair/checked";
    }
}
