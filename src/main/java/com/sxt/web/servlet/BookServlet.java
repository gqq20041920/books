package com.sxt.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.sxt.web.servlet.entity.BookDB;
import com.sxt.web.servlet.entity.TypeDB;
import com.sxt.web.servlet.entity.UserDB;

import service.BookService;
import service.TypeService;
import utils.MD5;
import utils.PageTool;
import utils.PaginationUtils;
import utils.ResBean;

@WebServlet("/book")
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID=1L;
	private BookService bookService=new BookService();
	
	private TypeService typeService=new TypeService();
	//分页查询
		// UserServlet.java
		public void listByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   UserDB userDB=(UserDB) request.getSession().getAttribute("userDB");
		   //根据当前登录的用户获取角色
		   Integer role=userDB.getRole();
		
		    	String word=request.getParameter("word");
		    	String currentPage=request.getParameter("pageNum");
		    	String pageSize=request.getParameter("pageSize");
		        PageTool<BookDB> pageTool = bookService.listByPage(currentPage,pageSize,word,null);
		        String path="book?method=listByPage";
		        if(word!=null&&word!= "")
		        {
		        	path +="&word="+ word;
		        }
		        //生成前端的分页按钮
		        String pagation=PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),pageTool.getPageSize(), path);
		         List<TypeDB>typeList=typeService.list(null,null);
		        request.setAttribute("pagation",pagation);
		        request.setAttribute("typeList",typeList);
		       request.setAttribute("word",word);
		        request.setAttribute("bList",pageTool.getRows());
		        //根据role判断跳转的页面
		        if(role==1) {
		        	//普通用户
		        	request.getRequestDispatcher("user/select.jsp").forward(request, response);
		        }else {
		        	//管理员		     
		      	     request.getRequestDispatcher("admin/admin_book.jsp").forward(request, response);
		        }
		        
		}
		//图书名称校验
	    public void checkBook(HttpServletRequest request, HttpServletResponse response) throws IOException {

	    	String bookName=request.getParameter("bookName");
	    	List<BookDB>list=bookService.list(bookName);
	    	ResBean resBean=new ResBean();
	    	if(list!=null&&list.size()>0) {
	    		resBean.setCode(400);
	    		resBean.setMsg("图书名称已存在");
	    	}else {
	    		resBean.setCode(200);
	    		resBean.setMsg("图书名称可以使用");
	    	}
	    	//将resBean 转化成json字符串
	    	Gson gson=new Gson();
	    	String json=gson.toJson(resBean);
	    	response.getWriter().print(json);
	    }
	    //添加图书
	    public void addBook(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
	    	       BookDB bookDB=new BookDB();
				  BeanUtils.populate(bookDB,request.getParameterMap());
				   bookService.addBook(bookDB);
				    response.sendRedirect("book?method=listByPage");
				}
	  //修改图书信息
		public void updBook(HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
			    BookDB bookDB = new BookDB();
			    BeanUtils.populate(bookDB, request.getParameterMap());
			    bookService.updBook(bookDB);
			    response.sendRedirect("book?method=listByPage");
		}
		//删除图书
	    public void delBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String bid=request.getParameter("bid");
		    	bookService.delBook(bid);  
		        request.getRequestDispatcher("book?method=listByPage").forward(request, response);		   
	}
	    //用户借阅
	    public void borrowBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	//要借阅的图书id
	    	String bid=request.getParameter("bid");
	    	 //获取当前用户信息    	
	       UserDB userDB=(UserDB) request.getSession().getAttribute("userDB");
		    	bookService.borrowBook(userDB,bid);  
		        request.getRequestDispatcher("history?method=list").forward(request, response);		   
	}
	    //图书归还
	    public void backBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	//要归还的记录
	    	String hid=request.getParameter("hid");
	    	 bookService.backBook(hid); 
		     request.getRequestDispatcher("history?method=backList").forward(request, response);		   
	}
	    //热门图书推荐
	    public void rank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			   UserDB userDB=(UserDB) request.getSession().getAttribute("userDB");
			   //根据当前登录的用户获取角色
			   Integer role=userDB.getRole();
			
			    	
			    	String currentPage=request.getParameter("pageNum");
			    	String pageSize=request.getParameter("pageSize");
			        PageTool<BookDB> pageTool = bookService.listByPage(currentPage,pageSize,null,1);
			        String path="book?method=rank";
			       
			        //生成前端的分页按钮
			        String pagation=PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),pageTool.getPageSize(), path);
			         
			        request.setAttribute("pagation",pagation);
			       
			       request.setAttribute("start",pageTool.getStartIndex());
			        request.setAttribute("bList",pageTool.getRows());
			        //根据role判断跳转的页面
			        if(role==1) {
			        	//普通用户
			        	request.getRequestDispatcher("user/bdtimes.jsp").forward(request, response);
			        }else {
			        	//管理员		     
			      	     request.getRequestDispatcher("admin/admin_bdtimes.jsp").forward(request, response);
			        }
			        
			}
}
