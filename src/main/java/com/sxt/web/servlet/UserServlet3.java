package com.sxt.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.sxt.web.servlet.entity.UserDB;

import service.UserService;
import utils.MD5;
import utils.PageTool;
import utils.PaginationUtils;
import utils.ResBean;
@WebServlet("/user")
public class UserServlet3 extends BaseServlet{
	private static final long serialVersionUID=1L;
	private UserService userService=new UserService();
	//用户列表
	// UserServlet.java
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	    	String currentPage=request.getParameter("pageNum");
	    	String pageSize=request.getParameter("pageSize");
	        PageTool<UserDB> pageTool = userService.list(currentPage,pageSize,null);
	        //生成前端的分页按钮
	        String pagation=PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),pageTool.getPageSize(), "user?method=list");
	       request.setAttribute("pagation",pagation);
	        request.setAttribute("uList",pageTool.getRows());
	        request.getRequestDispatcher("admin/admin_user.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 可以设置错误信息到请求属性中，然后转发到错误页面
	        request.setAttribute("errorMessage", "获取用户列表失败");
	        request.getRequestDispatcher("error.jsp").forward(request, response);
	    }
	}
	//添加用户
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException 
		{
			String account=request.getParameter("account");
//			String password=request.getParameter("password");
//			String name=request.getParameter("name");
//			String phone=request.getParameter("phone");
//			String maxNum=request.getParameter("maxNum");
//			String lendNum=request.getParameter("lendNum");
//			String role=request.getParameter("role");
//			
//			 // 检查参数是否为空
//		    if (account == null || account.trim().isEmpty() ||
//		        password == null || password.trim().isEmpty() ||
//		        name == null || name.trim().isEmpty() ||
//		        phone == null || phone.trim().isEmpty() ||
//		        maxNum == null || maxNum.trim().isEmpty() ||
//		        lendNum == null || lendNum.trim().isEmpty() ||
//		        role == null || role.trim().isEmpty()) {
//		        response.getWriter().write("参数不能为空");
//		        return;
//		    }
//		    
//			UserDB userDB=new UserDB();
//			userDB.setAccount(account);
//			userDB.setPassword(password);
//			userDB.setName(name);
//			userDB.setPhone(phone);
//			userDB.setMaxNum(Integer.parseInt(maxNum));
//			userDB.setLendNum(Integer.parseInt(lendNum));
//			userDB.setRole(Integer.parseInt(role));
//			System.out.println(userDB);
//	BeanUtils的简单使用    
				    UserDB userDB = new UserDB();
				    BeanUtils.populate(userDB, request.getParameterMap());
				    
				    boolean hasError = false;
				    StringBuilder errorMsg = new StringBuilder();

				    // 验证电话号码
				    String phone = userDB.getPhone();
				    if (phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
				        hasError = true;
				        errorMsg.append("手机号格式不正确\\n");
				    }
                    userDB.setTimes(0);//第一次添加的用户借阅量是0
                    userDB.setPassword(MD5.valueOf(userDB.getPassword()));//密码加密
				    userService.addUser(userDB);
				    response.sendRedirect("user?method=list");
				}
	//校验用户账号是否存在
	public void checkUser(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
	String account =request.getParameter("account");
	UserDB userDB=new UserDB();
	userDB.setAccount(account);
	List<UserDB>list=userService.getList(userDB);
	ResBean resBean=new ResBean();
	if(list!=null&&list.size()>0) {
		resBean.setCode(400);
		resBean.setMsg("账号被占用");
	}else {
		resBean.setCode(200);
		resBean.setMsg("账号可以使用");
	}
	//将resBean 转化成json字符串
	Gson gson=new Gson();
	String json=gson.toJson(resBean);
	response.getWriter().print(json);
	}
	//修改用户信息
	public void updUser(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		    UserDB userDB = new UserDB();
		    BeanUtils.populate(userDB, request.getParameterMap());
		    userService.updUser(userDB);
		    response.sendRedirect("user?method=list");
	}
	//删除用户信息
	public void delUser(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		   String uid=request.getParameter("uid");
		   userService.delUser(Integer.parseInt(uid));
		    response.sendRedirect("user?method=list");
	}
	//最佳读者排行
	public void rank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB=(UserDB) request.getSession().getAttribute("userDB");
		   //根据当前登录的用户获取角色
		   Integer role=userDB.getRole();
		
	    try {
	    	String currentPage=request.getParameter("pageNum");
	    	String pageSize=request.getParameter("pageSize");
	        PageTool<UserDB> pageTool = userService.list(currentPage,pageSize,1);
	        //生成前端的分页按钮
	        String pagation=PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),pageTool.getPageSize(), "user?method=rank");
	       request.setAttribute("pagation",pagation);
	       request.setAttribute("start",pageTool.getStartIndex());
	        request.setAttribute("uList",pageTool.getRows());
	        //根据role判断跳转的页面
	        if(role==1) {
	        	//普通用户
	        	request.getRequestDispatcher("user/brtimes.jsp").forward(request, response);
	        }else {
	        	//管理员		     
	      	     request.getRequestDispatcher("admin/admin_brtimes.jsp").forward(request, response);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 可以设置错误信息到请求属性中，然后转发到错误页面
	        request.setAttribute("errorMessage", "获取用户列表失败");
	        request.getRequestDispatcher("error.jsp").forward(request, response);
	    }
	}
}
	

