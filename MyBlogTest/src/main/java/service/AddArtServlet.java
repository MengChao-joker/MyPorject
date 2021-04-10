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

@WebServlet("/addart")
public class AddArtServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        int uid = 0;
        //1.从前端获取参数
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        //2.操作数据库
        if (title != null && content != null && !title.equals("") && !content.equals("")) {
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("uid") != null && (int) session.getAttribute("uid") > 0) {
                uid = (int) session.getAttribute("uid");
                ArticleInfoDao articleInfoDao = new ArticleInfoDao();
                try {
                    state = articleInfoDao.addArt(title, content, uid) > 0 ? 200 : 100;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                msg = "登陆异常~请先登录！";
            }
            //3.返回结果给前端
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", msg);
            res.put("state", state);
            RespUtils.Resp(resp, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
