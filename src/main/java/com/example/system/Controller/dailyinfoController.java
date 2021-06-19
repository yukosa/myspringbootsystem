package com.example.system.Controller;



import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.system.bean.*;
import com.example.system.mapper.UserMapper;
import com.example.system.service.DailyinfoExcelService;
import com.example.system.service.DailyinfoService;
import com.example.system.service.UserService;
import com.example.system.service.WhiteListExcelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
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
    @Autowired
    DailyinfoExcelService dailyinfoExcelService;
    @Autowired
    WhiteListExcelService whiteListExcelService;
    @RequestMapping("/user/teacher/dailyinfo/global")
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
            Object obj1 = session.getAttribute("identity");
            String loginIdentity = (String) obj1;                    // 强制转换成 String
            // 如果权限不足就返回主界面
            if (loginIdentity.equals("0")) {
                return "redirect:/index";
            }
            String loginname = (String) obj;
            int loginId = Integer.parseInt(loginname);
            List<Dailyinfo> dailyinfos= dailyinfoService.queryAll();
            model.addAttribute("dailyinfos",dailyinfos);

//            System.out.println("分页数据"+dailyinfos);
            PageInfo<Dailyinfo> pageInfo = new PageInfo<Dailyinfo>(dailyinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("maxDate",simpleDateFormat.format(dailyinfoService.getMaxDate()));
        model.addAttribute("minDate",simpleDateFormat.format(dailyinfoService.getMinDate()));
        return "teacher/tables";
    }

    @RequestMapping("/user/teacher/dailyinfo/local")
    public String usermanage(Model model,
                             @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                             @RequestParam(defaultValue="5",value="pageSize")Integer pageSize,
                             HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;   //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;    //设置默认每页显示的数据数
        };
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
            List<Dailyinfo> dailyinfos= dailyinfoService.queryAll();
            model.addAttribute("dailyinfos",dailyinfos);
            PageInfo<Dailyinfo> pageInfo = new PageInfo<Dailyinfo>(dailyinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "teacher/tables::ld";
    }


    //学生日常信息填报查询
    @RequestMapping("/user/dailyinfo/querry")
    public String dailyquerry(Model model,HttpServletRequest request,
                               @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                               @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){
        String identity;
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
            identity= (String) session.getAttribute("identity");
            List<Dailyinfo> dailyinfos = dailyinfoService.queryById(loginId);

//            System.out.println("分页数据"+dailyinfos);
            PageInfo<Dailyinfo> pageInfo = new PageInfo<Dailyinfo>(dailyinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        if(identity.equals("0")){
            return "student/dailyquerry";
        }
        else{
            return "teacher/dailyquerry";
        }

    }


//    老师日常信息填报查询
    @RequestMapping("/user/teacher/dailyinfo/querry")
    public String gloabalfresh2(Model model,HttpServletRequest request,
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
            List<Dailyinfo> dailyinfos = dailyinfoService.queryById(loginId);

//            System.out.println("分页数据"+dailyinfos);
            PageInfo<Dailyinfo> pageInfo = new PageInfo<Dailyinfo>(dailyinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "teacher/dailyquerry";
    }


    @GetMapping("/user/dailyinfo/download")
    public void download(HttpServletResponse response, @RequestParam(defaultValue = "1",value="date")String date) throws IOException {
        ExcelWriter writer = null;
        OutputStream out = null;
        try {
            List<DailyinfoExcel> dailyinfoExcelList = dailyinfoExcelService.queryByDate(date);
            System.out.println(dailyinfoExcelList);
            out = response.getOutputStream();
            writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            String fileName = date+"健康信息表";
            Sheet sheet = new Sheet(1, 0, DailyinfoExcel.class);
            sheet.setSheetName("每日健康信息");
            writer.write(dailyinfoExcelList, sheet);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "ISO8859-1"));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.finish();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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


    @RequestMapping("/user/teacher/dailyinfo/querry/day/{num}")
    public String querryday2(Model model,HttpServletRequest request,@PathVariable("num")int num){

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
            Dailyinfo dailyinfo = dailyinfoService.queryByNum(num);
            model.addAttribute("dailyinfo",dailyinfo);
        } finally {
            PageHelper.clearPage();
        }

        return "teacher/mydaily";
    }

//    学生日常信息填报

    @RequestMapping("/user/dailyinfo/write")
    public String dailywrite(Model model,HttpServletRequest request){

        String identity;
        try {
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            String loginname = (String) obj;
            identity= (String) session.getAttribute("identity");
            int loginId = Integer.parseInt(loginname);
            Date date = new Date(System.currentTimeMillis());
            model.addAttribute("id", loginId);
            model.addAttribute("date", date);
        } finally {
            PageHelper.clearPage();
        }
        if(identity.equals("0") ){
            return "student/dailywrite";
        }
        else{
            return "teacher/dailywrite";
        }
    }
//    老师日常信息填报

    @RequestMapping("/user/teacher/dailyinfo/write")
    public String dailywrite2(Model model,HttpServletRequest request){

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
            Date date = new Date(System.currentTimeMillis());
            model.addAttribute("id", loginId);
            model.addAttribute("date", date);
        } finally {
            PageHelper.clearPage();
        }

        return "teacher/dailywrite";
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
    @GetMapping("/user/teacher/dailyinfo/whitelist")
    public String createWhiteList(Model model,HttpServletRequest request){
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
        List<Basicinformation> whitelist=dailyinfoService.getWhiteList();
        model.addAttribute("whitelist",whitelist);
        return "teacher/whiteList";
    }

    @GetMapping("/user/dailyinfo/getWhiteList")
    public void download(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream out = null;
        try {
            List<WhiteListExcel> whiteList=whiteListExcelService.getWhiteList();
            out = response.getOutputStream();
            writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today=simpleDateFormat.format(new Date());
            String fileName =  today+ "白名单";
            Sheet sheet = new Sheet(1, 0, WhiteListExcel.class);
            sheet.setSheetName(today+ "白名单");
            writer.write(whiteList, sheet);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "ISO8859-1"));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.finish();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @GetMapping("/user/teacher/dailyinfo/write/add")
    public String toAddpage2(){
        return "teacher/dailyquerry";
    }

    @PostMapping("/user/teacher/dailyinfo/write/add")
    public String AddInfo2(Dailyinfo dailyinfo,HttpServletRequest request){
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
        dailyinfoService.addinfo(dailyinfo);

        return "redirect:/user/teacher/dailyinfo/querry";
    }

    @RequestMapping("/user/quickdailyinfo")
    public String quickdailywrite(Model model,HttpServletRequest request) {

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
        return "quickdailywrite";
    }

    @PostMapping("/quickdailyinfo/write/add")
    public String quickAddInfo(Dailyinfo dailyinfo,Model model,HttpServletRequest request){
        dailyinfoService.addinfo(dailyinfo);
        model.addAttribute("msg","填报成功");
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
        return "quickdailywrite";
    }
}