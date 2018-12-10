package com.leehao.mall.web.servlet;

import com.leehao.mall.domain.Cart;
import com.leehao.mall.domain.CartItem;
import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;
import com.leehao.mall.domain.User;
import com.leehao.mall.service.OrderService;
import com.leehao.mall.service.serviceImpl.OrderServiceImpl;
import com.leehao.mall.utils.MyBeanUtils;
import com.leehao.mall.utils.UUIDUtils;
import com.leehao.mall.web.base.BaseServlet;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	
	public String addOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取订单-----将购物车的中数据提交
		//1..确认用户已登录
		User user = (User) req.getSession().getAttribute("loginUser");
		if (null == user) {
			//未登录
			req.setAttribute("msg", "请登录之后再下单");
			return "/jsp/login.jsp";
		}
		//获取购物车
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		//构造订单
		Orders order = new Orders();
		order.setOid(UUIDUtils.getId());
		order.setOrdertime(new Date());
		order.setState(1);
		order.setTotal(cart.getTotal());
		order.setUser(user);
//		System.out.println(order);
		//构造订单项
//		OrderItem orderItem = new OrderItem();
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getId());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getNum());
			orderItem.setOrder(order);
			orderItem.setTotal(item.getSubTotal());
			//将每一个明细添加到订单中
			order.getOrderItems().add(orderItem);
			
		}
				
		//调用service，添加订单
		OrderService orderService = new OrderServiceImpl();
		orderService.addOrder(order);
//		
//		//清空购物车，转发
		cart.clearCart();
		req.setAttribute("order", order);
		
		return "/jsp/order_info.jsp";
	}

}
