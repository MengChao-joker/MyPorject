package org.example.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
@WebServlet("/test")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String http = req.getScheme();//http
            String ip = req.getServerName();//ip或域名
            int port = req.getServerPort();//port
            String context = req.getContextPath();//应用上下文路径
        System.out.println(http+"://"+ip+":"+port+context);

    }
}
