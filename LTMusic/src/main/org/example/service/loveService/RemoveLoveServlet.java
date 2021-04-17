package org.example.service.loveService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.LoveDao;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/removeLoveServlet")
public class RemoveLoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        boolean msg = false;
        String sId = req.getParameter("id");
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) req.getSession(false).getAttribute("user");
            LoveDao loveDao = new LoveDao();
            msg = loveDao.removeLoveMusicById(user.getId(), Integer.parseInt(sId));
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        mapper.writeValue(resp.getWriter(), map);
    }
}
