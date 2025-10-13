package com.sxt.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sxt.web.servlet.entity.UserDB;

import service.UserService;
import utils.MD5;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService=new UserService();
    //用户登录
 // LoginServlet.java 修改后的 login 方法
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        
      
            // 1. 后端校验非空
            if (account == null || account.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                session.setAttribute("msg", "账号或密码不能为空");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            
            // 2. 加密并查询数据库
            String encryptedPwd = MD5.valueOf(password.trim());
            UserDB userDB = userService.login(account.trim(), encryptedPwd);
            
            if (userDB == null) {
                session.setAttribute("msg", "账号或密码错误");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else {
                session.setAttribute("userDB", userDB);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        
    }
}