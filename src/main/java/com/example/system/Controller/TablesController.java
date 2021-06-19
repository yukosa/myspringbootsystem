package com.example.system.Controller;


//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.metadata.Sheet;
//import com.alibaba.excel.support.ExcelTypeEnum;
//import com.yjh.mapper.DailyinfoExcelMapper;
//import com.yjh.mapper.DailyinfoMapper;
//import com.yjh.pojo.Dailyinfo;
//import com.yjh.pojo.DailyinfoExcel;
import com.example.system.bean.Dailyinfo;
import com.example.system.service.DailyinfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.List;

@Controller
public class TablesController {


    @Autowired
    DailyinfoService dailyinfoService;

//    @Autowired
//    DailyinfoExcelMapper dailyinfoExcelMapper;

    @RequestMapping("/tables")
    public String queryAll(Model model,HttpServletRequest request,
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
            List<Dailyinfo> dailyinfos=dailyinfoService.queryAll();

//            System.out.println("分页数据"+dailyinfos);
            PageInfo<Dailyinfo> pageInfo = new PageInfo<Dailyinfo>(dailyinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }

        return "/tables";
    }



//    @GetMapping("/user/dailyinfo/download")
//    public void download(HttpServletResponse response, @RequestParam(defaultValue = "1",value="date")String date) throws IOException {
//        ExcelWriter writer = null;
//        OutputStream out = null;
//        try {
//            List<DailyinfoExcel> dailyinfoExcelList = dailyinfoExcelMapper.queryByDate(date);
//            System.out.println(dailyinfoExcelList);
//            out = response.getOutputStream();
//            writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
//            String fileName = date+"健康信息表";
//            Sheet sheet = new Sheet(1, 0, DailyinfoExcel.class);
//            sheet.setSheetName("每日健康信息");
//            writer.write(dailyinfoExcelList, sheet);
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "ISO8859-1"));
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (writer != null) {
//                writer.finish();
//            }
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
