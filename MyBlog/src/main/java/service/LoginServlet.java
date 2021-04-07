package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ArticleInfoDao;
import dao.UserInfoDao;
import models.ArticleInfo;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //判空
        if (username != null && password != null) {
            // 调用数据库验证用户名和密码
            UserInfo userInfo = new UserInfo();
            userInfo.setUsename(username);
            userInfo.setPassword(password);
            UserInfoDao userInfoDao = new UserInfoDao();
            try {
//                state = ((userInfoDao.isLogin(userInfo)) ? 200 : 100);
                userInfo = userInfoDao.getUserInfo(userInfo);
                if(userInfo!=null&&userInfo.getId()>=1){
                    state = 200;
                    //如果登陆成功，需要创建一个session信息
                    HttpSession session = request.getSession();
                    //写入当前对象信息
                    session.setAttribute("userinfo",userInfo);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            state = 100;
            msg = "操作失败，数据不全！";
        }
        //将信息返回给前台
        Map<String, Object> map = new HashMap<>();
        map.put("state", state);///////////////////////
        map.put("msg", msg);
        ResultJSONUtils.resp(response, map);
    }
}
