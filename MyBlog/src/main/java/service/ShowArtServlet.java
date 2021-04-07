package service;

import dao.ArticleInfoDao;
import models.ArticleInfo;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShowArtServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleInfo articleInfo = null;
        String msg ="";
        int state = 0;
        int id = Integer.parseInt(request.getParameter("id"));
        if(id>0){
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                articleInfo = articleInfoDao.showArticle(id);
                state = 1;
                System.out.println(articleInfo);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            msg = "参数有误！";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("articleinfo",articleInfo);
        map.put("state",state);
        ResultJSONUtils.resp(response,map);
    }
}
