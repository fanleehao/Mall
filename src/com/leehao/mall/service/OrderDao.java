package com.leehao.mall.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;

public interface OrderDao {

	void addOrder(Connection conn, Orders order)throws SQLException;

	void addOrderItems(Connection conn, OrderItem item) throws SQLException;

}
