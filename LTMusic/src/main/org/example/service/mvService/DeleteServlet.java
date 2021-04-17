package org.example.service.mvService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dao.MusicDao;
import org.example.models.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/deleteMV")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        int id = Integer.parseInt(req.getParameter("id"));
        MusicDao musicDao = new MusicDao();
        Music music = musicDao.findMusicByID(id);
        Map<String,Object> map = new HashMap<>();
        boolean msg = musicDao.deleteMusicById(id,music);//返回值为true即数据库和本地资源均已删除
        map.put("msg", msg);
        ObjectMapper mapper = new ObjectMapper();
        String retJson = mapper.writeValueAsString(map);
        resp.getWriter().println(retJson);
    }
}
