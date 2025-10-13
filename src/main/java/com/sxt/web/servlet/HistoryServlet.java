package com.sxt.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sxt.web.servlet.entity.HistoryDB;

import com.sxt.web.servlet.entity.UserDB;

import service.HistoryService;
import utils.DateUtils;
import utils.PageTool;
import utils.PaginationUtils;

@WebServlet("/history")
public class HistoryServlet extends BaseServlet{

	/**
	 * 图书借阅历史记录
	 */
	private static final long serialVersionUID = 1L;
 private HistoryService historyService=new HistoryService();
 //查询正在被借阅的图书
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			   UserDB userDB=(UserDB) request.getSession().getAttribute("userDB");
			   //根据当前登录的用户获取角色
			   Integer role=userDB.getRole();
			
			    	
			    	String currentPage=request.getParameter("pageNum");
			    	String pageSize=request.getParameter("pageSize");
			    	
			    	//普通用户只能查询自己的管理员查询所有
			    	PageTool<HistoryDB> pageTool=null;
			    	if(role==1) {
			    		//普通用户
			    	pageTool=historyService.listByPage(currentPage, pageSize, userDB.getUid(), 1);
			    	}
			    	else {
			    		//管理员
			    		pageTool=historyService.listByPage(currentPage, pageSize, null, 1);
			    	}
			        
			        String path="history?method=list";
			       
			        //生成前端的分页按钮
			        String pagation=PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),pageTool.getPageSize(), path);
			        
			        request.setAttribute("pagation",pagation);
			       
			        request.setAttribute("hList",pageTool.getRows());
			        //根据role判断跳转的页面
			        if(role==1) {
			        	//普通用户
			        	request.getRequestDispatcher("user/borrow.jsp").forward(request, response);
			        }else {
			        	//管理员		     
			      	     request.getRequestDispatcher("admin/admin_borrow.jsp").forward(request, response);
			        }
			        
			}
	
  //查询已经被归还的图书记录
    public void backList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			   UserDB userDB=(UserDB) request.getSession().getAttribute("userDB");
			   //根据当前登录的用户获取角色
			   Integer role=userDB.getRole();
			
			    	
			    	String currentPage=request.getParameter("pageNum");
			    	String pageSize=request.getParameter("pageSize");
			    	
			    	//普通用户只能查询自己的管理员查询所有
			    	PageTool<HistoryDB> pageTool=null;
			    	if(role==1) {
			    		//普通用户
			    	pageTool=historyService.listByPage(currentPage, pageSize, userDB.getUid(), 2);
			    	}
			    	else {
			    		//管理员
			    		pageTool=historyService.listByPage(currentPage, pageSize, null, 2);
			    	}
			        
			        String path="history?method=backList";
			       
			        //生成前端的分页按钮
			        String pagation=PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),pageTool.getPageSize(), path);
			        
			        request.setAttribute("pagation",pagation);
			       
			        request.setAttribute("hList",pageTool.getRows());
			        //根据role判断跳转的页面
			        if(role==1) {
			        	//普通用户
			        	request.getRequestDispatcher("user/history.jsp").forward(request, response);
			        }else {
			        	//管理员		     
			      	     request.getRequestDispatcher("admin/admin_history.jsp").forward(request, response);
			        }
			        
			}
    //延期
    public void delay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String hid = request.getParameter("hid");
        String endTimeStr = request.getParameter("endtime");
        
        try {
            // 转换hid为整数
            int historyId = Integer.parseInt(hid);
            
            // 定义日期时间格式（根据前端传入的格式调整）
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            // 解析字符串为LocalDateTime
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
            
            // 创建并设置HistoryDB对象
            HistoryDB historyDB = new HistoryDB();
            historyDB.setHid(historyId);
            historyDB.setEndTime(endTime); // 确保setEndTime接受LocalDateTime
            
            // 更新历史记录
            historyService.updHistory(historyDB);
            
            // 转发请求
            request.getRequestDispatcher("history?method=list").forward(request, response);
            
        } catch (NumberFormatException e) {
            // 处理hid参数格式错误
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid History ID格式");
        } catch (DateTimeParseException e) {
            // 处理日期解析错误
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "日期格式不正确，需使用 yyyy-MM-dd HH:mm:ss");
        }
    }
    
	
}
