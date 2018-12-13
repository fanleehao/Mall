package com.leehao.mall.web.servlet;

import com.leehao.mall.domain.Category;
import com.leehao.mall.service.CategoryService;
import com.leehao.mall.service.serviceImpl.CategoryServiceImpl;
import com.leehao.mall.utils.UUIDUtils;
import com.leehao.mall.web.base.BaseServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	CategoryService categoryService = new CategoryServiceImpl();
	
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		//查询		
		List<Category> list = categoryService.getAllCats();
		//放入request,转发
		req.setAttribute("allCats", list);
		return "/admin/category/list.jsp";
	}
	public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}
	
	//添加分类
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取参数，构造对象
		String cname = req.getParameter("cname");
//		System.out.println("cname==" + cname);
		Category category = new Category(UUIDUtils.getId(), cname);
		//查询分类是否已存在
		//添加分类
//		System.out.println(category.getCname());
		categoryService.addCategory(category);		
		//重定向
		resp.sendRedirect("/Mall/AdminCategoryServlet?method=findAllCats");
		return null;
	}

	//编辑	editCategoryUI
	public String editCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取参数
		String cid = req.getParameter("cid");
		Category category = categoryService.findCategoryById(cid);
		req.setAttribute("category", category);
		return "/admin/category/edit.jsp";
	}
	//editCategory
	public String editCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	
		String cid = req.getParameter("cid");
		String cname = req.getParameter("cname");
		Category category = new Category(cid, cname);   //使用populate更合适
		categoryService.editCategory(category);
		//重定向编辑页
		resp.sendRedirect("/Mall/AdminCategoryServlet?method=findAllCats");
		return null;
	}
	
	//deleteCategory 删除
	public String deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	
		String cid = req.getParameter("cid");
		categoryService.deleteCategoryById(cid);
		resp.sendRedirect("/Mall/AdminCategoryServlet?method=findAllCats");
		return null;
	}
}
