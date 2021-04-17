package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.UserDao;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/logOut")
public class LogOutServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端请求
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        boolean msg = false;
        String news = "";
        if(req.getSession(false)!=null||req.getSession(false).getAttribute("user")!=null) {
            User user = (User) req.getSession(false).getAttribute("user");
            String username = user.getUsername();
            UserDao userDao = new UserDao();
            msg = userDao.logout(username);
        }else {
            news = "注销失败！请重试！";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("news", news);
        ObjectMapper mapper = new ObjectMapper();
        String retJson = mapper.writeValueAsString(map);
        resp.getWriter().println(retJson);
    }
}
