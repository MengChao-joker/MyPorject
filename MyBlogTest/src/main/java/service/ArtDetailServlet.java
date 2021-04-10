package service;

import com.mysql.jdbc.util.ResultSetUtil;
import dao.ArticleInfoDao;
import models.ArticleInfo;
import models.ArticleInfoVO;
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

@WebServlet("/detail")
public class ArtDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        ArticleInfoVO art = new ArticleInfoVO();
        //1.从前端获取参数
        int id = Integer.parseInt(req.getParameter("id"));
        //2.操作数据库
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            art = articleInfoDao.getArtDetail(id);
            state = 200;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //3.返回结果给前端
        HashMap<String, Object> res = new HashMap<>();
        res.put("msg", msg);
        res.put("art", art);
        res.put("state", state);
        RespUtils.Resp(resp, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
