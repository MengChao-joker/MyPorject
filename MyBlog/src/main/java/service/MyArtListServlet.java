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
import java.util.List;
import java.util.Map;

public class MyArtListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        List<ArticleInfo> list = null;
        //1.权限验证->UID
        HttpSession session = request.getSession();
        if(session==null||session.getAttribute("userinfo")==null){
            //用户未登录
            state = 100;
            msg = "用户未登录";
        }else {
            //2.去数据库查询列表信息
            UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
            int uid = userInfo.getId();
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                list = articleInfoDao.getMyArtList(uid);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //3.返回信息
        Map<String, Object> map = new HashMap<>();
        map.put("state", state);///////////////////////
        map.put("msg", msg);
        map.put("list",list);
        ResultJSONUtils.resp(response, map);
    }
}
