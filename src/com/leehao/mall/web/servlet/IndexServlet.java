package com.leehao.mall.web.servlet;

import com.leehao.mall.domain.Category;
import com.leehao.mall.service.CategoryService;
import com.leehao.mall.service.serviceImpl.CategoryServiceImpl;
import com.leehao.mall.web.base.BaseServlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 调用业务层，返回分类集合，转发到主页
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		//集合放入request
		req.setAttribute("catsList", list);
		//转发到jsp/index.jsp		
		return "/jsp/index.jsp";
	}
    
}
