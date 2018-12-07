package com.leehao.mall.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leehao.mall.domain.User;
import com.leehao.mall.service.UserService;
import com.leehao.mall.service.serviceImpl.UserServiceImpl;
import com.leehao.mall.utils.CookUtils;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//转请求
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response =(HttpServletResponse) resp;
		
		//登录页直接放行
		String servletPath= request.getServletPath();
		if (servletPath.startsWith("/UserServlet")) {
			String method = request.getParameter("method");
			if("loginUI".equals(method)){
				chain.doFilter(request, response);
				return;
			}
		}
		
		//1 登录用户
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		
		//如果用户已登录,放行
		if (loginUser != null) {
			chain.doFilter(request, response);
			return;
		}
		//获得自动登录的cookie信息,没有就说明不需要自动登录
		Cookie userCookie = CookUtils.getCookieByName("autoLoginCookie", request.getCookies());
		if(userCookie == null){
			chain.doFilter(request, response);
			return;
		}
		//否则根据cooki信息查询用户，登录
		String[] u = userCookie.getValue().split("@");
		String username = u[0];
		String password = u[1];
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
//		System.out.println("daozheli.." + password);
		//执行登录
		try {
			UserService userService = new UserServiceImpl();
			loginUser = userService.userLogin(user);
			if (loginUser == null) {
				chain.doFilter(request, response);
//				System.out.println("888888");
//				request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
//				System.out.println("999999");
				return;
			}
			//否则自动登录
			request.getSession().setAttribute("loginUser", loginUser);
			chain.doFilter(request, response);
		} catch (Exception e) {
//			System.out.println("自动登录异常，自动忽略");
			//登录失败
			String msg = e.getMessage();
//			resp.getWriter().println("<script>alert(" + msg + ")</script>");
//			System.out.println(msg);
//			request.setAttribute("msg", msg);
//			response.sendRedirect("/jsp/login.jsp");
			chain.doFilter(request, response);
//			System.out.println("23333");
		}
	
		
	}
}
