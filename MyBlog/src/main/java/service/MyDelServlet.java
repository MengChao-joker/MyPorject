package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ArticleInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MyDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg = "";
//        UserInfo userInfo = null;
        //1.获取前端参数
        int id = Integer.parseInt(request.getParameter("id"));
        if(id>0){//参数有效
            //2.操作数据库(对数据库进行文章删除)
//            HttpSession session = request.getSession();
//            userInfo = (UserInfo)session.getAttribute("userinfo");
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            int res = 0;
            try {
                res = articleInfoDao.delArticleByID(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if(res>0){
                //删除成功
                succ = 1;
            }
        }else {
            msg = "参数有误！";
        }
        //3.返回结果给前端
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("succ",succ);
        System.out.println(map);
        ResultJSONUtils.resp(response,map);
    }
}
