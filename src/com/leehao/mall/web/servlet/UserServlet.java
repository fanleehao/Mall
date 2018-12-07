package com.leehao.mall.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leehao.mall.domain.User;
import com.leehao.mall.service.UserService;
import com.leehao.mall.service.serviceImpl.UserServiceImpl;
import com.leehao.mall.utils.CookUtils;
import com.leehao.mall.utils.MailUtils;
import com.leehao.mall.utils.MyBeanUtils;
import com.leehao.mall.utils.UUIDUtils;
import com.leehao.mall.web.base.BaseServlet;
import com.sun.mail.handlers.message_rfc822;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	
    //返回到注册jsp
    public String registUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	return "/jsp/register.jsp";
    }
    //验证可否注册 ---ajax异步
    public String userExists(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取传递参数
    	String username = req.getParameter("username");
    	UserService userService = new UserServiceImpl();
    	User user = userService.findUserByUserName(username);
    	if (user != null) {
			resp.getWriter().print("11");
		}else {
			resp.getWriter().print("00");
		}
    	return null;
	}
    //注册
    public String userRegist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.获取表单的参数----从注册页来,键值对，值可能是多个，所以是string数组-----表单中的name和value
    	Map<String, String[]> map = req.getParameterMap();
    	    	
/*    	Set<String> keySet = map.keySet();
    	Iterator<String> iterator = keySet.iterator();    	
    	while (iterator.hasNext()) {
			String key = iterator.next();
			String [] strs = map.get(key);
			System.out.println(key);
			for (String string : strs) {
				System.out.println(string);
			}
			System.out.println();
		}*/
    	
    	
    	//将表单数据填充到对象中
    	User user = new User();
    	MyBeanUtils.populate(user, map);
    	System.out.println(user);
    	//額外 
    	user.setState(0);
    	user.setUid(UUIDUtils.getId());
    	user.setCode(UUIDUtils.getCode());    	
    	
    	//2.注册用户service
    	UserService userService = new UserServiceImpl();
    	try {
    		//使用异常判断是否注册成功————————用返回boolean值也可
			userService.userRegist(user);
			//成功ze发送邮件-----给注册邮箱发送激活码
			MailUtils.sendMail(user.getEmail(), user.getCode());
			req.setAttribute("msg", "用户注册成功！请前往注册邮箱激活！");
		} catch (Exception e) {
			//失败则提示
			req.setAttribute("msg", "用户注册失败！请重新注册！");
		}
    	
    	
    	//跳转提示页面
    	return "/jsp/info.jsp";
	}
    //激活
    public String active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
    	//接受code,调用验证是否能激活，修改状态码，删除激活码，否则提示不能激活
    	String code = req.getParameter("code");
    	UserService userService = new UserServiceImpl();
    	boolean isActive = userService.userActive(code);
    	if (isActive == true) {
			//激活成功,提示request，转发页面到登录
    		req.setAttribute("msg", "成功激活，请登录！");
    		return "/jsp/login.jsp";
		}else {
			req.setAttribute("msg", "激活失败，请重新注册！");
			return "/jsp/info.jsp";
		}   	
    	
    }
     
    //登录转向
    public String loginUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	return "/jsp/login.jsp";
    }
    //登录控制
    public String userLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//获取哟用户数据
    	User user = new User();
    	MyBeanUtils.populate(user, req.getParameterMap());
    	//调用登录功能---失败或成功
    	UserService userService = new UserServiceImpl();    	
    	try {
    		//登录成功，无异常 ----加载session,转发到首页
			User loginUser = userService.userLogin(user);			  
			
			//关于自动登录，通过cookie结合Filter过滤器使用
			String autoLogin = req.getParameter("autoLogin");			
			if ("1".equals(autoLogin)) {
				//勾选了下次自动登录，则设置cookie
				Cookie autoLoginCookie = new Cookie("autoLoginCookie", loginUser.getUsername() 
						+ "@" + loginUser.getPassword());
				autoLoginCookie.setPath("/");    //设置项目根目录下所有url可见
				autoLoginCookie.setMaxAge(60*60*24*7);  //一周有效期
				resp.addCookie(autoLoginCookie);
			}else {
				//否则删除cookie  ---如果有，会将上次的cookie删除
				Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
				autoLoginCookie.setPath("/");
				autoLoginCookie.setMaxAge(0);
				resp.addCookie(autoLoginCookie);
			}
			
			//记住用户名
			String remUser = req.getParameter("remUser");
			if ("yes".equals(remUser)) {
				//用户选中自动登录复选框
				Cookie remUserCookie=new Cookie("remUserCookie", loginUser.getUsername());
				remUserCookie.setPath("/");
				remUserCookie.setMaxAge(60*60*24*7);
				resp.addCookie(remUserCookie);
			}else {
				//否则删除cookie  ---如果有，会将上次的cookie删除
				Cookie remUserCookie = new Cookie("remUserCookie", "");
				remUserCookie.setPath("/");
				remUserCookie.setMaxAge(0);
				resp.addCookie(remUserCookie);
			}
			
			
			//转发
			req.getSession().setAttribute("loginUser", loginUser);
//			resp.sendRedirect("/Mall/index.jsp"); 
			resp.sendRedirect(req.getContextPath()+"/");
			
			
			return null;
		} catch (Exception e) {
			//登录失败
			String msg = e.getMessage();
			System.out.println(msg);
			req.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
    }

    //退出控制
    public String logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//清楚session
    	req.getSession().invalidate();
    	Cookie autoLoginCookie = CookUtils.getCookieByName("autoLoginCookie", req.getCookies());		
    	//清除cookie
    	if (autoLoginCookie != null) {
    		autoLoginCookie.setPath("/");
    		autoLoginCookie.setMaxAge(0);
    		resp.addCookie(autoLoginCookie);
		}
    	//重定向
    	resp.sendRedirect("/Mall/index.jsp");
    	return null;
    }
    
}
