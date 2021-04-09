package service;

import dao.ArticleInfoDao;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/upcount")
public class UpCountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int succ = 0;
        String msg = "";
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            int res = articleInfoDao.upCount(id);
            if(res>0){
                succ = 1;
            }else{
                msg="数据库操作失败！";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("succ",succ);
        map.put("msg",msg);
        ResultJSONUtils.resp(resp,map);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
