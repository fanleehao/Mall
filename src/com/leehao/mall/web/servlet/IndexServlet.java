package com.leehao.mall.web.servlet;

import com.leehao.mall.domain.Category;
import com.leehao.mall.domain.Product;
import com.leehao.mall.service.CategoryService;
import com.leehao.mall.service.ProductService;
import com.leehao.mall.service.serviceImpl.CategoryServiceImpl;
import com.leehao.mall.service.serviceImpl.ProductServiceImpl;
import com.leehao.mall.web.base.BaseServlet;

import oracle.jrockit.jfr.openmbean.ProducerDescriptorType;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		/* 通过ajax异步加载了已经  ----不会再执行到此   2.0版本
		 * // 调用业务层，返回分类集合，转发到主页
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		//集合放入request
		req.setAttribute("catsList", list);*/
		//转发到jsp/index.jsp	
		
		
		//首页  1. 显示最新商品   2. 显示最热门的商品（按时间最新）
		//调用业务层，返回两个集合，传给request
		ProductService productService = new ProductServiceImpl();
		
		List<Product> newsProductList = productService.findNews();		
		req.setAttribute("newsProductList", newsProductList);
		List<Product> hotsProductList = productService.findHots();
		req.setAttribute("hotsProductList", hotsProductList);
//		System.out.println("---------");
//		System.out.println(hotsProductList);
		
		return "/jsp/index.jsp";
	}
    
}
