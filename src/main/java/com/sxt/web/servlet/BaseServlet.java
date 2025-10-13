package com.sxt.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.MyException;

public class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        // 设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            // 获取方法名
            String methodName = request.getParameter("method");
        
            // 获取当前类
            Class<? extends BaseServlet> clazz = this.getClass();

            // 获取方法
            Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            // 调用方法
            method.invoke(this, request, response);
        }
        	catch (InvocationTargetException e) {
        	    Throwable cause = e.getTargetException();  // 关键：提取真实异常        	    
        	    if (cause instanceof MyException) {  // 正确判断
        	        request.setAttribute("msg", cause.getMessage());
        	    }else {
        	    	 request.setAttribute("msg","网络异常");
        	    	 e.printStackTrace(); 
        	    }
                     
            // 跳转到错误页面
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("BaseServlet异常处理: " + e.getMessage());
            request.setAttribute("msg","网络波动");
            // 跳转到错误页面
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}