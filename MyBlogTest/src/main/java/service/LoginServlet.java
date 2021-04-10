package service;

import dao.UserInfoDao;
import models.UserInfo;
import utils.RespUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username!=null&&password!=null&&
           !username.equals("")&&!password.equals("")){
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            UserInfoDao userInfoDao = new UserInfoDao();
            try {
//                state = userInfoDao.isLogin(userInfo)>0?200:100;
                userInfo = userInfoDao.isLogin(userInfo);
                if(userInfo!=null&&userInfo.getId()>=1){
                    state = 200;
                    HttpSession session = request.getSession();
                    session.setAttribute("uid",userInfo.getId());
                }               
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            msg = "参数有误！";
        }
        //返回数据给前端
        HashMap<String,Object> resp = new HashMap<>();
        resp.put("msg",msg);
        resp.put("state",state);
        RespUtils.Resp(response,resp);
    }
}
