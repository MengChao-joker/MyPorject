package service;

import dao.ArticleInfoDao;
import models.ArticleInfo;
import utils.RespUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/del")
public class DelArtServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        int uid = 0;
        int id = Integer.parseInt(req.getParameter("id"));
        if(id>0){
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("uid") != null && (int) session.getAttribute("uid") > 0) {
                uid = (int) session.getAttribute("uid");
                ArticleInfoDao articleInfoDao = new ArticleInfoDao();
                try {
                    state = articleInfoDao.delArt(id,uid)>0?200:100;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                msg = "登陆异常~请先登录！";
            }
        }else {
            msg = "参数有误！";
        }
        HashMap<String,Object> res = new HashMap<>();
        res.put("state",state);
        res.put("msg",msg);
        RespUtils.Resp(resp,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
