package service;

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
import java.util.List;

@WebServlet("/pubal")
public class PubArtListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int state = -1;
        String msg = "";
        List<ArticleInfoVO> list = null;
        int psize = Integer.parseInt(req.getParameter("psize"));
        int curpage = Integer.parseInt(req.getParameter("curpage"));
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            list = articleInfoDao.getAllArtList(curpage, psize);
            state = 200;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("state", state);
        res.put("msg", msg);
        res.put("list", list);
        RespUtils.Resp(resp, res);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
