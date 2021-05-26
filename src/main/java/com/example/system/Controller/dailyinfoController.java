package com.example.system.Controller;



import com.example.system.bean.Dailyinfo;
import com.example.system.service.DailyinfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class dailyinfoController {

    @Autowired
    DailyinfoService dailyinfoService;


    @RequestMapping("/user/dailyinfo/global")
    public String gloabalfresh(Model model){
        model.addAttribute("dailyinfos",null);
        model.addAttribute("pageInfo", null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("maxDate",simpleDateFormat.format(dailyinfoService.getMaxDate()));
        model.addAttribute("minDate",simpleDateFormat.format(dailyinfoService.getMinDate()));
        return "tables";
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
        return "tables::ld";
    }


}