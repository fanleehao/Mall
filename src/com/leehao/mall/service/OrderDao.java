package com.leehao.mall.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;
import com.leehao.mall.domain.User;

import jdk.nashorn.internal.runtime.ECMAErrors;

public interface OrderDao {

	void addOrder(Connection conn, Orders order)throws SQLException;

	void addOrderItems(Connection conn, OrderItem item) throws SQLException;

	int findAllRecords(User user) throws Exception;

	List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void updateOrder(Orders order) throws Exception;

	List<Orders> findAllOrders()throws Exception;

	List<Orders> findAllOrders(String state)throws Exception;

}
