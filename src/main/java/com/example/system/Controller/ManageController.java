package com.example.system.Controller;

import com.example.system.bean.Basicinformation;
import com.example.system.bean.User;
import com.example.system.service.BasicinformationService;
import com.example.system.service.DailyinfoService;
import com.example.system.service.UserService;
import com.example.system.serviceImpl.BasicinformationServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ManageController {

    //    ------------------------------管理主页-------------------------------------------
    //管理主页进入
    @RequestMapping("/user/manage")      // 管理页面网址
    public String manage(HttpServletRequest request) {
//        System.out.println("?AAAAAAAAAAAAAAAAAAAAAAAA");
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("identity");
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

//    -------------------------------------用户管理-------------------------------------------------

    //    用户修改页面
    @RequestMapping("/user/manage/muser")
    public String globalfresh(Model model,
                              HttpServletRequest request,
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
            HttpSession session = request.getSession();       // 获取登录信息
            Object obj = session.getAttribute("username");
            if (obj == null) {     // 登录信息为 null，表示没有登录
                return "redirect:/login";
            }
            Object obj1 = session.getAttribute("identity");
            String loginIdentity = (String) obj1;                    // 强制转换成 String
            // 如果权限不足就返回主界面
            if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
                return "redirect:/index";
            }
            List<User> userinfos = userService.getAllUser();
//            System.out.println("分页数据"+userinfos);
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

    //    用户添加页面
    @GetMapping("/user/manage/muser/add")
    public String toAddpage(HttpServletRequest request) {
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        return "/manage/adduser";
    }

    //    用户添加响应
    @PostMapping("/user/manage/muser/add")
    public String Adduser(User user,HttpServletRequest request) {
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        userService.addUser(user);

        return "redirect:/user/manage/muser";
    }

    //    用户编辑页面
    @GetMapping("/user/manage/muser/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, Model model,HttpServletRequest request) {
//        int idd = Integer.parseInt(id);
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/manage/edituser";
    }

    //    用户编辑响应
    @PostMapping("/user/manage/muser/edit")
    public String Edituser(User user,HttpServletRequest request) {
//        userService.addUser(user);
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        userService.modifyUser(user);
        return "redirect:/user/manage/muser";
    }

    //    用户删除请求
    @GetMapping("/user/manage/muser/delete/{id}")
    public String toDelete(@PathVariable("id") Integer id, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        userService.dropUser(id);
        return "redirect:/user/manage/muser";
    }

    //    导出用户表
    @RequestMapping("/user/manage/muser/downloadexcel")
    public void Downloaduser(HttpServletResponse response) throws IOException {
//        userService.addUser(user);
//        userService.modifyUser(user);
        //表头数据
        String[] header = {"学工号", "姓名", "密码", "用户身份"};
        List<User> users = userService.getAllUser();

        //数据内容
//        String[] student1 = {"1", "小红", "女", "23"};
//        String[] student2 = {"2", "小强", "男", "26"};
//        String[] student3 = {"3", "小明", "男", "28"};

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称为"用户表"
        HSSFSheet sheet = workbook.createSheet("用户表");

        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(10);

        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);

        //遍历添加表头(下面模拟遍历学生，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }

        //模拟遍历结果集，把内容加入表格
        //模拟遍历第一个学生
        for (int i = 0; i < users.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell cell = row.createCell(0);
            HSSFRichTextString text = new HSSFRichTextString(String.valueOf(users.get(i).getId()));
            cell.setCellValue(text);

//            HSSFRow row = sheet.createRow(i+1);
            cell = row.createCell(1);
            text = new HSSFRichTextString(users.get(i).getUserName());
            cell.setCellValue(text);

//            row = sheet.createRow(i+1);
            cell = row.createCell(2);
            text = new HSSFRichTextString(users.get(i).getPwd());
            cell.setCellValue(text);

//            row = sheet.createRow(i+1);
            cell = row.createCell(3);
            text = new HSSFRichTextString(users.get(i).getIdentity());
            cell.setCellValue(text);
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //这后面可以设置导出Excel的名称，此例中名为student.xls
        response.setHeader("Content-disposition", "attachment;filename=user.xls");

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());


//        return "redirect:/user/manage/muser";
    }


    //--------------------------------基本信息管理-------------------------------------------
    @Autowired
    BasicinformationService basicinformationService;

    //    基本信息修改主页
    @RequestMapping("/user/manage/mbasicinfo")
    public String getBasic(Model model,
                           HttpServletRequest request,
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
            if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
                return "redirect:/index";
            }
            List<Basicinformation> basicinfos = basicinformationService.getAllUser();
//            System.out.println("分页数据"+basicinfos);
            PageInfo<Basicinformation> pageInfo = new PageInfo<Basicinformation>(basicinfos, pageSize);
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage();
        }
        return "manage/mbasicinfo";

    }

    //    用户信息添加页面
    @GetMapping("/user/manage/mbasicinfo/add")
    public String toAddBasic(HttpServletRequest request) {

        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        return "/manage/addbasicinfo";
    }

    //    用户信息添加响应
    @PostMapping("/user/manage/mbasicinfo/add")
    public String AddBasic(Basicinformation basicinformation) {
//        userService.addUser(user);
        basicinformationService.insertUser(basicinformation);
        return "redirect:/user/manage/mbasicinfo";
    }


    //    用户信息编辑页面
    @GetMapping("/user/manage/mbasicinfo/edit/{id}")
    public String toEditt(@PathVariable("id") Integer id, Model model,HttpServletRequest request) {
//        int idd = Integer.parseInt(id);
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        Basicinformation basicinformation = basicinformationService.selectUserById(id);
//        User user =  userService.getUserById(id);
        model.addAttribute("basicinfo", basicinformation);
        return "/manage/editbasicinfo";
    }

    //    用户信息编辑响应
    @PostMapping("/user/manage/mbasicinfo/edit")
    public String Editbasicinfo(Basicinformation basicinformation,HttpServletRequest request) {
//        userService.addUser(user);
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        basicinformationService.updateUser(basicinformation);
//        userService.modifyUser(user);
        return "redirect:/user/manage/mbasicinfo";
    }

    //    用户信息删除请求
    @GetMapping("/user/manage/mbasicinfo/delete/{id}")
    public String Deletebasicinfo(@PathVariable("id") Integer id, Model model,HttpServletRequest request) {
//        userService.dropUser(id);
        HttpSession session = request.getSession();       // 获取登录信息
        Object obj = session.getAttribute("username");
        if (obj == null) {     // 登录信息为 null，表示没有登录
            return "redirect:/login";
        }
        Object obj1 = session.getAttribute("identity");
        String loginIdentity = (String) obj1;                    // 强制转换成 String
        // 如果权限不足就返回主界面
        if (loginIdentity.equals("0")||loginIdentity.equals("1")) {
            return "redirect:/index";
        }
        basicinformationService.deleteUser(id);
        return "redirect:/user/manage/mbasicinfo";
    }


    //    导出用户基本信息表
    @RequestMapping("/user/manage/mbasicinfo/downloadexcel")
    public void Downloadbasicinfo(HttpServletResponse response) throws IOException {
//        userService.addUser(user);
//        userService.modifyUser(user);
        //表头数据
        String[] header = {"学工号", "姓名", "性别", "年龄", "身份证号", "电话", "学院", "住所", "校区", "原籍地", "现户籍地", "现居住地"};
        List<Basicinformation> basicinfos = basicinformationService.getAllUser();


        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称为"用户表"
        HSSFSheet sheet = workbook.createSheet("用基本信息表");

        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(10);

        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);

        //遍历添加表头(下面模拟遍历学生，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }

        //模拟遍历结果集，把内容加入表格
        //模拟遍历每个学生
//        "学工号", "姓名", "性别", "年龄","身份证号","电话","学院","住所","校区","原籍地","现户籍地","现居住地"
        for (int i = 0; i < basicinfos.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell cell = row.createCell(0);
            HSSFRichTextString text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getId()));
            cell.setCellValue(text);

//            HSSFRow row = sheet.createRow(i+1);
            cell = row.createCell(1);
            text = new HSSFRichTextString(basicinfos.get(i).getName());
            cell.setCellValue(text);

//            row = sheet.createRow(i+1);
            cell = row.createCell(2);
            text = new HSSFRichTextString(basicinfos.get(i).getSex());
            cell.setCellValue(text);

//            row = sheet.createRow(i+1);
            cell = row.createCell(3);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getIdCardNo()));
            cell.setCellValue(text);

            cell = row.createCell(4);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getPhoneNum()));
            cell.setCellValue(text);

            cell = row.createCell(5);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getCollege()));
            cell.setCellValue(text);

            cell = row.createCell(6);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getResidence()));
            cell.setCellValue(text);

            cell = row.createCell(7);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getCampus()));
            cell.setCellValue(text);

            cell = row.createCell(8);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getPlaceOfOrigin()));
            cell.setCellValue(text);

            cell = row.createCell(9);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getPlaceOfDomicile()));
            cell.setCellValue(text);

            cell = row.createCell(3);
            text = new HSSFRichTextString(String.valueOf(basicinfos.get(i).getCurrentResidence()));
            cell.setCellValue(text);
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //这后面可以设置导出Excel的名称，此例中名为student.xls
        response.setHeader("Content-disposition", "attachment;filename=basicinfo.xls");

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());


//        return "redirect:/user/manage/muser";
    }
}