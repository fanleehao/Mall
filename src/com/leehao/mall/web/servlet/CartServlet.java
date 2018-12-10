package com.leehao.mall.web.servlet;

import com.leehao.mall.domain.Cart;
import com.leehao.mall.domain.CartItem;
import com.leehao.mall.domain.Product;
import com.leehao.mall.service.ProductService;
import com.leehao.mall.service.serviceImpl.ProductServiceImpl;
import com.leehao.mall.web.base.BaseServlet;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {

	/**
	 * 将商品添加至购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		int quatity = Integer.parseInt(req.getParameter("quantity"));
		String pid = req.getParameter("pid");
		//调用service,将购物项加入购物车
		//从session中获取购物车
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		if (null == cart) {
			//为空
			cart = new Cart();
			req.getSession().setAttribute("cart", cart);
			
		} 
		
		//已存在
		//通过id和数量，更新购物车
		ProductService productService = new ProductServiceImpl();
		Product product = productService.finProductById(pid);
		//构造待添加的item
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setNum(quatity);
		
		//添加到购物车
		cart.addCartItemToCar(cartItem);
		//重定向    重定向时，是重新发起请求，要保留请求的全限定，转发是在同样的项目下 -----重定向刷新时不会重复提交
		resp.sendRedirect("/Mall/jsp/cart.jsp");
//		return "/jsp/cart.jsp";
		return null;
	}
	
	//删除
	public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取session
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		String pid = req.getParameter("pid");
		cart.removeCartItem(pid);
		
		resp.sendRedirect("/Mall/jsp/cart.jsp");
		
		return null;
	}
	
	//清空
	public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		cart.clearCart();
		resp.sendRedirect("/Mall/jsp/cart.jsp");
		return null;
	}
}
