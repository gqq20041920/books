package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//正常来说只有登录后才可以进行一系列操作，所以我们需要添加过滤器，
//阻止通过地址访问进入界面的操作
@WebFilter("/*")//所有请求都会被过滤
public class LoginFiter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//强转请求和响应
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		//获取请求的路径
		String uri=req.getRequestURI();
		//路径判断，部分内容不拦截
		if(uri.contains("/login")||uri.contains("/static")) {
			//放行
			chain.doFilter(req, res);
		}else {
			//验证是否登录
			Object userDB=req.getSession().getAttribute("userDB");
			if(userDB!=null) {
				//已登录，放行
				chain.doFilter(req, res);
			}else
			{
				//未登录
				res.sendRedirect("login.jsp");
			}
		}
				
		
	}
	

}
