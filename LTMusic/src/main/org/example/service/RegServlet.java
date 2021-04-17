package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/regServlet")
public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端请求
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        boolean msg = false;
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(!username.equals("")&&!password.equals("")){
            UserDao userDao = new UserDao();
            msg = userDao.reg(username,password);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msg", msg);
        ObjectMapper objectMapper = new ObjectMapper();
        String retJson = objectMapper.writeValueAsString(map);
        resp.getWriter().println(retJson);
    }
}
