package com.example.system.Controller;

import com.example.system.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    NoticeMapper noticeMapper;

    @GetMapping("/user/getNotice")
    @ResponseBody
    public String getNotice(Model model){
        String newNotice = noticeMapper.getNewNotice();
        return newNotice;
//        model.addAttribute("newNotice",newNotice);
//        System.out.println("消息："+newNotice);
    }



}
