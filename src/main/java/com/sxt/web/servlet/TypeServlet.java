package com.sxt.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;
import com.sxt.web.servlet.entity.TypeDB;
import book.TypeDao;
import service.TypeService;
import utils.PageTool;
import utils.PaginationUtils;
import utils.ResBean;
@WebServlet("/type")
public class TypeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private TypeService typeService=new TypeService();
	//用户列表
		// UserServlet.java
	public void listByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    try {
		    	String currentPage=request.getParameter("pageNum");
		    	String pageSize=request.getParameter("pageSize");
		        PageTool<TypeDB> pageTool = typeService.listByPage(currentPage,pageSize);
		        //生成前端的分页按钮
		        String pagation=PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),pageTool.getPageSize(), "type?method=listByPage");
		       request.setAttribute("pagation",pagation);
		        request.setAttribute("tList",pageTool.getRows());
		        request.getRequestDispatcher("admin/admin_booktype.jsp").forward(request, response);
		    } catch (Exception e) {
		        e.printStackTrace();
		        // 可以设置错误信息到请求属性中，然后转发到错误页面
		        request.setAttribute("errorMessage", "获取用户列表失败");
		        request.getRequestDispatcher("error.jsp").forward(request, response);
		    }
	}
	//类型名称校验
		    public void checkType(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		    	String tid=request.getParameter("tid");
		    	String typeName=request.getParameter("typeName");
		    	List<TypeDB>list=typeService.list(null,typeName);
		    	ResBean resBean=new ResBean();
		    	if(list!=null&&list.size()>0) {
		    		resBean.setCode(400);
		    		resBean.setMsg("类别名称已存在");
		    	}else {
		    		resBean.setCode(200);
		    		resBean.setMsg("类别名称可以使用");
		    	}
		    	//将resBean 转化成json字符串
		    	Gson gson=new Gson();
		    	String json=gson.toJson(resBean);
		    	response.getWriter().print(json);
		    }
		    //类型添加
		    public void addType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			   
			    	String typeName=request.getParameter("typeName");
			    	typeService.addType(typeName);
			   
			        request.getRequestDispatcher("type?method=listByPage").forward(request, response);
			   
		}
		    //修改类型
		    public void updType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    	String tid=request.getParameter("tid");
			    	String typeName=request.getParameter("typeName");
			    	TypeDB typeDB=new TypeDB();
			    	typeDB.setTid(Integer.parseInt(tid));
			    	typeDB.setTypeName(typeName);
			    	typeService.updType(typeDB);
			   
			        request.getRequestDispatcher("type?method=listByPage").forward(request, response);
			   
		}
		  //删除类型
		    public void delType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    	String tid=request.getParameter("tid");
			    	typeService.delType(Integer.parseInt(tid));  
			        request.getRequestDispatcher("type?method=listByPage").forward(request, response);
			   
		}
		}

