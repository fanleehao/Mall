package com.leehao.mall.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leehao.mall.domain.Orders;
import com.leehao.mall.service.OrderService;
import com.leehao.mall.service.serviceImpl.OrderServiceImpl;
import com.leehao.mall.web.base.BaseServlet;

import net.sf.json.JSONArray;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	OrderService orderService = new OrderServiceImpl();
	//获取所有订单
	public String findAllOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String state = req.getParameter("state");
		List<Orders> allOrders;
		if(state == null || "".equals(state)){
			allOrders = orderService.findAllOrders();
		}else {
			allOrders = orderService.findAllOrders(state);
		}
		 
		req.setAttribute("allOrders", allOrders);		
		return "/admin/order/list.jsp";
	}
	//findOrderItemsByOidWithAjax
	public String findOrderItemsByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String oid = req.getParameter("oid");
		Orders orders = orderService.findOrderByOid(oid);
		String jString = JSONArray.fromObject(orders.getOrderItems()).toString();
		
		//设置返回格式
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().println(jString);
		return null;
	}
}
