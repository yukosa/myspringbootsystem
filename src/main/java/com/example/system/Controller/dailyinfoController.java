package com.example.system.Controller;



import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.system.bean.Dailyinfo;
import com.example.system.bean.DailyinfoExcel;
import com.example.system.bean.User;
import com.example.system.service.DailyinfoExcelService;
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

    @RequestMapping("/user/dailyinfo/global")
    public String gloabalfresh(Model model){
        model.addAttribute("dailyinfos",null);
        model.addAttribute("pageInfo", null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("maxDate",simpleDateFormat.format(dailyinfoService.getMaxDate()));
        model.addAttribute("minDate",simpleDateFormat.format(dailyinfoService.getMinDate()));
        return "teacher/tables";
    }

    @RequestMapping("/user/dailyinfo/local")
    public String usermanage(Model model,
                             @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                             @RequestParam(defaultValue="5",value="pageSize")Integer pageSize) {
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
            List<Dailyinfo> dailyinfos= dailyinfoService.queryAll();
            model.addAttribute("dailyinfos",dailyinfos);
            PageInfo<Dailyinfo> pageInfo = new PageInfo<Dailyinfo>(dailyinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "teacher/tables::ld";
    }



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