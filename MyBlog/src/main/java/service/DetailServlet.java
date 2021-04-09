package service;

import dao.ArticleInfoDao;
import models.ArticleInfoVO;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int succ = 0;
        String msg = "";
        ArticleInfoVO articleInfoVO = null;
        int id = Integer.parseInt(req.getParameter("id"));
        if(id>0){
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();
            try {
                articleInfoVO = articleInfoDao.getDetail(id);
                succ=1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            msg="参数有误！";
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("succ",succ);
        map.put("art",articleInfoVO);
        ResultJSONUtils.resp(resp,map);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
