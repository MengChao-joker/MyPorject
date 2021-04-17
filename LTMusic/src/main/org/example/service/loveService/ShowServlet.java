package org.example.service.loveService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.LoveDao;
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

@WebServlet("/findLoveMusic")
public class ShowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String msg = "";
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(401);
            System.out.println("请先登录！");
            msg = "请先登录~";
        } else {
            User user = (User) session.getAttribute("user");
            int uid = user.getId();
            String musicName = req.getParameter("loveMusicName");
            System.out.println(musicName);
            LoveDao loveDao = new LoveDao();
            List<Music> musicList = null;
            if (musicName == null) {
                musicList = loveDao.showLoveMusic(uid);
            } else {
                musicList = loveDao.fuzzySearchMusic(uid, musicName);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), musicList);
        }

    }
}
