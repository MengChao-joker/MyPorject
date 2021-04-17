package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.AdminDao;
import org.example.Dao.MusicDao;
import org.example.models.Music;
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

@WebServlet("/deleteSelMusicServlet")
public class DeleteSelMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Map<String, Object> map = new HashMap<>();
        boolean msg = false;
        String[] values = req.getParameterValues("id[]");
        MusicDao musicDao = new MusicDao();
        AdminDao adminDao = new AdminDao();
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");
        if(adminDao.findUser(user)) {
            map.put("msg", musicDao.deleteMusicById(values));
        }else {
            map.put("msg",false);
        }
        ObjectMapper mapper = new ObjectMapper();
        String retJson = mapper.writeValueAsString(map);
        resp.getWriter().println(retJson);
    }
}
