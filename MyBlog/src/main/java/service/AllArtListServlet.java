package service;

import dao.ArticleInfoDao;
import models.ArticleInfoVO;
import utils.ResultJSONUtils;

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

@WebServlet("/allartlist")
public class AllArtListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg ="";
        List<ArticleInfoVO> list = null;
        int succ = 0;
        int psize = Integer.parseInt(req.getParameter("psize"));
        int cpage = Integer.parseInt(req.getParameter("currentpage"));
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            list = articleInfoDao.getAllList(psize,cpage);
            if(list==null){
                msg = "没有文章可查";
            }else{
                succ = 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("list",list);
        map.put("succ",succ);
        ResultJSONUtils.resp(resp,map);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
