package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.UserInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//用户注册
public class RegServlet extends HttpServlet {
    Gson gson = new GsonBuilder().create();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json");
        int state = -1;//200表示接口执行成功
        String msg = "";
        //1.接受前端请求
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter writer = response.getWriter();
        if(username==null||password==null){
            msg = "123";
        }else {
            //操作数据库进行插入操作
            UserInfo userInfo = new UserInfo();
            userInfo.setUsename(username);
            userInfo.setPassword(password);
            //调用操作数据库中的方法
            UserInfoDao userInfoDao = new UserInfoDao();
            try {
                boolean res = userInfoDao.add(userInfo);
                if(res){
                    state=200;
                }else{
                    state=100;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("state",state);
            map.put("msg",msg);
            ObjectMapper mapper = new ObjectMapper();
            ResultJSONUtils.resp(response,map);
            //{"state":100,"msg":"msg"}
//            writer.println("{\"state\":"+state+",\"msg\":\""+msg+"\"}");

        }
    }
}
