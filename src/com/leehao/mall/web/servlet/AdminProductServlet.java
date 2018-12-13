package com.leehao.mall.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leehao.mall.domain.Category;
import com.leehao.mall.domain.PageModel;
import com.leehao.mall.domain.Product;
import com.leehao.mall.service.CategoryService;
import com.leehao.mall.service.ProductService;
import com.leehao.mall.service.serviceImpl.CategoryServiceImpl;
import com.leehao.mall.service.serviceImpl.ProductServiceImpl;
import com.leehao.mall.utils.MyBeanUtils;
import com.leehao.mall.web.base.BaseServlet;


@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	ProductService productService = new ProductServiceImpl();
	//商品管理，查询显示---带分页的
	public String findAllProductsWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取参数
		int curPageNum = Integer.parseInt(req.getParameter("num"));
		int pageSize = 5;
		//2.调用业务层
		PageModel pageModel = productService.findAllProductsWithPage(curPageNum, pageSize);
		//3.获取结果，set和转发
		req.setAttribute("pageModel", pageModel);
		return "/admin/product/list.jsp";
	}

	//添加UI    addProductUI
	public String addProductUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {	
		//设置分类信息并转发
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.getAllCats();
		
		req.setAttribute("categories", categories);
		return "/admin/product/add.jsp";
	}
	//添加商品
	public String addProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取参数
		Product product = new Product();
		MyBeanUtils.populate(product, req.getParameterMap());
		//调用service
		
		//设置，转发
		return "/admin/product/list.jsp";
	}
	
}
