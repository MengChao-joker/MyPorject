package service;

import dao.ArticleInfoDao;
import models.Favorite;
import utils.RespUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/flist")
public class FavoriteList extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        List<Favorite> list = new ArrayList<>();
        HttpSession session = req.getSession(false);
        if(session!=null&&(int)session.getAttribute("uid")>0) {
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                list = articleInfoDao.getFavoList((int)session.getAttribute("uid"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            msg = "登陆异常！请先登录！";
        }
        if(list==null) {
            state = 100;
            msg="您还没有收藏文章！";
        }else {
            state = 200;
        }
        HashMap<String,Object> res = new HashMap<>();
        res.put("msg",msg);
        res.put("state",state);
        res.put("list",list);
        RespUtils.Resp(resp,res);
    }
}
