package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserInfoDao;
import models.UserInfo;
import utils.RespUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        //1.从前端获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username!=null&&password!=null
                &&!username.equals("")
                &&!password.equals("")) {
            //2.操作数据库
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            UserInfoDao userInfoDao = new UserInfoDao();
            try {
                state = userInfoDao.reg(userInfo)>0?200:100;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            //参数有误！
            msg = "参数有误！";
        }
        //3.返回结果给前端
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("applicant/json");
//        PrintWriter writer = response.getWriter();
        HashMap<String,Object> resp = new HashMap<>();
        resp.put("state",state);
        resp.put("msg",msg);
        RespUtils.Resp(response,resp);
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writeValueAsString(resp));
//        writer.println(mapper.writeValueAsString(resp));
    }
}
