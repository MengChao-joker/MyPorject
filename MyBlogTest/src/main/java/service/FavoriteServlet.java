package service;

import dao.UserInfoDao;
import models.ArticleInfo;
import models.ArticleInfoVO;
import models.Favorite;
import models.UserInfo;
import utils.RespUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        int uid = 0;
        //获取前端参数
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");
//        Date createtime = (Date)req.getParameter("time");
        String username = req.getParameter("username");
        //获取当前用户session信息
        HttpSession session = req.getSession(false);
        if(session!=null&&session.getAttribute("uid")!=null
                &&(int)session.getAttribute("uid")>0){
            uid = (int)session.getAttribute("uid");
        if (title != null && !title.equals("") &&
                content != null && !content.equals("") &&
                username != null && !username.equals("")) {
            //操作数据库
            Favorite favorite = new Favorite();
            favorite.setContent(content);
            favorite.setTitle(title);
            favorite.setUsername(username);
            UserInfoDao userInfoDao = new UserInfoDao();
            try {
                state = userInfoDao.favorite(favorite,uid,id) > 0 ? 200 : 100;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            msg = "参数有误！";
        }
        }else {
            msg = "登陆异常，请先登录！";
        }
        //返回信息给前端
        HashMap<String, Object> res = new HashMap<>();
        res.put("msg", msg);
        res.put("state", state);
        RespUtils.Resp(resp, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
