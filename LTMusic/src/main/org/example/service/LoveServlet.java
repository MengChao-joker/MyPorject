package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.LoveDao;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loveMusicServlet")
public class LoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        boolean msg = false;
        String sId = req.getParameter("id");
        if(!sId.equals("")&&req.getSession(false)!=null
                &&req.getSession(false).getAttribute("user")!=null){
            int id = Integer.parseInt(sId);
            User user = (User) req.getSession(false).getAttribute("user");
            LoveDao loveDao = new LoveDao();
            msg = loveDao.insertLoveMusic(user.getId(), id);
        }else {
            resp.setStatus(401);
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        map.put("msg", msg);
        String retJson = mapper.writeValueAsString(map);
        resp.getWriter().println(retJson);
    }
}
