package com.leehao.mall.service.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;
import com.leehao.mall.service.OrderDao;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOrder(Connection conn, Orders order) throws SQLException {
		//
		String sql="INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr=new QueryRunner();
		Object[] params={order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		qr.update(conn,sql,params);
	}

	@Override
	public void addOrderItems(Connection conn, OrderItem item) throws SQLException {
		String sql="INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner qr=new QueryRunner();
		Object[] params={item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
		qr.update(conn,sql,params);
		
	}

	

}
