package org.example.service;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@WebServlet("/showMusic")
public class ShowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String curPage = req.getParameter("curPage");
        int page = Integer.parseInt(curPage);
        String msg = "";
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(401);
            System.out.println("请先登录！");
            msg = "请先登录~";
        } else {
            User user = (User) session.getAttribute("user");
            String musicName = req.getParameter("musicName");
            MusicDao musicDao = new MusicDao();
            List<Music> musicList = null;
            if (musicName == null) {
                musicList = musicDao.findMusic(page);
            } else {
                musicList = musicDao.fuzzySearch(musicName,page);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), musicList);
        }
//        ObjectMapper mapper = new ObjectMapper();
//        String retJson = mapper.writeValueAsString(msg);
//        resp.getWriter().println(retJson);
    }
}
