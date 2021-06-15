package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.UserDao;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端请求
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String respCookie = "jmcCookie";
        Cookie cookie = new Cookie("cookie",respCookie);
        resp.addCookie(cookie);
        boolean msg = false;
        User user = null;
        //数据库操作
        if(username!=null&&password!=null){
            UserDao userDao = new UserDao();
            user = userDao.login(username,password);
            //存放登陆用户的Session信息
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            if(user!=null){
                msg = true;
            }
        }
        //返回请求
        Map<String,Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("user",user);
        ObjectMapper mapper = new ObjectMapper();
        String retJson = mapper.writeValueAsString(map);
        resp.getWriter().println(retJson);
    }
}
