package com.leehao.mall.web.servlet;

import com.leehao.mall.domain.Product;
import com.leehao.mall.service.ProductService;
import com.leehao.mall.service.serviceImpl.ProductServiceImpl;
import com.leehao.mall.web.base.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	public String findProductById(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String pid = req.getParameter("pid");
		ProductService productService = new ProductServiceImpl();
		Product product = productService.finProductById(pid);
		req.setAttribute("product", product);
//		System.out.println(product);
		return "/jsp/product_info.jsp";
	}
}
