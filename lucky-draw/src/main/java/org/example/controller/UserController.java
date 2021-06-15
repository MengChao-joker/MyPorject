package org.example.controller;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest req){
        if(user!=null) {
            //数据库查询
            User exist = userService.queryByUsername(user.getUsername());

            if (exist == null) throw new AppException("URR111", "用户不存在！");
            if (!exist.getPassword().equals(user.getPassword()))
                throw new AppException("U111", "用户名或密码错误！");
            HttpSession session = req.getSession();
            session.setAttribute("user", exist);
            return null;
        }else {
            throw new AppException("URR111", "请求数据错误！");
        }

    }

    @PostMapping("/register")
    //解析请求中的数据User中所对应的属性和图片文件名
    public Object register(User user, MultipartFile headFile){
        userService.register(user,headFile);
        return null;
    }

    @GetMapping("/logout")
    public Object logout(HttpSession session){
        session.removeAttribute("user");
        return null;
    }
}
