package com.example.system.Controller;


import com.example.system.bean.Notice;
import com.example.system.mapper.NoticeMapper;
import com.example.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class formsController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    NoticeMapper noticeMapper;

    @PostMapping("/user/forms")
    public String formsSubmit(@RequestParam("title")String title, @RequestParam("content")String content, @RequestParam("noticeType") String noticeType, @RequestParam("noticeTime") Date noticeTime, Model model, HttpSession session){
        model.addAttribute("msg",1);
        int id = Integer.parseInt((String) session.getAttribute("username"));
        Notice note = new Notice();
        note.setId(id);
        note.setContent(content);
        note.setType(noticeType);
//        SimpleDateFormat tmp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date tmp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(noticeTime);
        note.setTime(noticeTime);
//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("id",id);
//        map.put("type",noticeType);
//        map.put("content",content);
//        map.put("time",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(noticeTime));
//        System.out.println(NoticeTime);
        noticeMapper.saveNotice(note);
//        System.out.println("rsult"+s);
        return "forms";
    }

}
