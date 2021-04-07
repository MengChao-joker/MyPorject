package service;

import dao.ArticleInfoDao;
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
import java.util.Map;

public class MyAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg = "";
        UserInfo userInfo = null;
        //1.获取前端参数
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        //2.操作数据库
        if(title!=null&&content!=null){
        HttpSession session = request.getSession();
        userInfo = (UserInfo)session.getAttribute("userinfo");
        int uid = userInfo.getId();
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            int res = 0;
            try {
                res = articleInfoDao.addArticle(title,content,uid);
                if(res>0) {
                    succ = 1;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            msg = "参数有误";
        }
        //3.返回结果给前端
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("succ",succ);
        ResultJSONUtils.resp(response,map);
    }
}
