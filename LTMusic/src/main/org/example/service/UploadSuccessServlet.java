package org.example.service;

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
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/uploadSuccess")
public class UploadSuccessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String msg = "";

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String fileName = (String) session.getAttribute("filename");
        String title = fileName.split("\\.")[0];

        Music music = new Music();
        music.setTitle(title);
        music.setSinger(req.getParameter("singer"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String upload_time = sdf.format(new Date());
        music.setUpload_time(upload_time);
        music.setUrl("music/"+title);
        music.setUid(user.getId());
        MusicDao musicDao = new MusicDao();
        if(musicDao.insertMusic(music)==0){
            resp.getWriter().println("插入失败！");
        }else {
            resp.sendRedirect("list.html?username="+user.getUsername());
        }
    }
}
