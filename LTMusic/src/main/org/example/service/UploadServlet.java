package org.example.service;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.example.Utils.ServletUtils;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        HttpSession session = req.getSession(false);
        System.out.println(session);
        String filename = null;
        User user = null;
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            System.out.println("session:" + user);


            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileItems = null;
            try {
                fileItems = upload.parseRequest(req);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            assert fileItems != null;
            FileItem fileItem = fileItems.get(0);
            filename = fileItem.getName();
            try {
                fileItem.write(new File(ServletUtils.PATH, filename));
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.setAttribute("filename", filename);
            System.out.println("filename:" + session.getAttribute("filename"));
            resp.sendRedirect("uploadSuccess.html");
        } else {
            //用户未登录，请先登录！
            System.out.println("用户未登录，请先登录！");
            resp.getWriter().println("<h2>用户未登录，请先登录！" + "</h2>");
        }

    }
}

