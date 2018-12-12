package com.leehao.mall.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.leehao.mall.domain.User;

/**
 * Servlet Filter implementation class PrivilegeFilter
 */
@WebFilter(urlPatterns = { "/PrivilegeFilter", "/jsp/cart.jsp", "/jsp/order_info.jsp",
		"/jsp/order_list.jsp" }, servletNames = { "CartServlet", "OrderServlet" })
public class PrivilegeFilter implements Filter {

	public PrivilegeFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest myReq = (HttpServletRequest) request;
		// 判断当前的session中是否存在已经登录成功的用户
		User user = (User) myReq.getSession().getAttribute("loginUser");
		if (null != user) {
			// 如果存在,放行
			chain.doFilter(request, response);
		} else {
			// 如果不存在,转入到提示页面
			myReq.setAttribute("msg", "请登录后操作!");
			// 转入到提示页面
			myReq.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
