package com.leehao.mall.service;

import java.util.List;

import com.leehao.mall.domain.Orders;
import com.leehao.mall.domain.PageModel;
import com.leehao.mall.domain.User;

import jdk.nashorn.internal.runtime.ECMAErrors;

public interface OrderService {

	void addOrder(Orders order)throws Exception;

	PageModel findMyOrdersWithPage(User user, int curPageNum) throws Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void updateOrder(Orders order) throws Exception;

	List<Orders> findAllOrders() throws Exception;

	List<Orders> findAllOrders(String state) throws Exception;

}
