package com.leehao.mall.service.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;
import com.leehao.mall.domain.Product;
import com.leehao.mall.domain.User;
import com.leehao.mall.service.OrderDao;
import com.leehao.mall.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOrder(Connection conn, Orders order) throws SQLException {
		//
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
		qr.update(conn, sql, params);
	}

	@Override
	public void addOrderItems(Connection conn, OrderItem item) throws SQLException {
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { item.getItemid(), item.getQuantity(), item.getTotal(), item.getProduct().getPid(),
				item.getOrder().getOid() };
		qr.update(conn, sql, params);

	}

	@Override
	public int findAllRecords(User user) throws Exception {
		String sql = "select count(*) from orders where uid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Long value = (Long) queryRunner.query(sql, new ScalarHandler(), user.getUid());
		return value.intValue();
	}

	@Override
	public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
		// 查询订单，并把每一个订单下的订单项填充进去
		String sql = "select * from orders where uid = ? limit ?, ?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		List<Orders> orders = queryRunner.query(sql, new BeanListHandler<Orders>(Orders.class), user.getUid(),
				startIndex, pageSize);
		// 填充
		for (Orders order : orders) {
			// 获取每笔订单中，每个订单项和其产品的信息
			String sql1 = "select * from orderitem o, product p where o.pid=p.pid and oid =?";
			// 返回一个Map项 的list
			List<Map<String, Object>> mapLists = queryRunner.query(sql1, new MapListHandler(), order.getOid());

			// 遍历list
			for (Map<String, Object> map : mapLists) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
				// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);

				// 将map中属于orderItem的数据自动填充到orderItem对象上
				BeanUtils.populate(orderItem, map);
				// 将map中属于product的数据自动填充到product对象上
				BeanUtils.populate(product, map);

				// 让每个订单项和商品发生关联关系
				orderItem.setProduct(product);
				// 将每个订单项存入订单下的集合中
				order.getOrderItems().add(orderItem);

			}

		}

		return orders;
	}

	@Override
	public Orders findOrderByOid(String oid) throws Exception {
		String sql = "select * from orders where oid= ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Orders order = qr.query(sql, new BeanHandler<Orders>(Orders.class), oid);

		// 根据订单id查询订单下所有的订单项以及订单项对应的商品信息
		sql = "select * from orderitem o, product p where o.pid=p.pid and oid=?";
		List<Map<String, Object>> list02 = qr.query(sql, new MapListHandler(), oid);
		// 遍历list
		for (Map<String, Object> map : list02) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);

			// 将map中属于orderItem的数据自动填充到orderItem对象上
			BeanUtils.populate(orderItem, map);
			// 将map中属于product的数据自动填充到product对象上
			BeanUtils.populate(product, map);

			// 让每个订单项和商品发生关联关系
			orderItem.setProduct(product);
			// 将每个订单项存入订单下的集合中
			order.getOrderItems().add(orderItem);
		}
		return order;
	}

	@Override
	public void updateOrder(Orders order) throws Exception {
		// 更新订单信息
		String sql = "UPDATE orders SET ordertime=? ,total=? ,state= ?, address=?,NAME=?, telephone =? WHERE oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(),
				order.getName(), order.getTelephone(), order.getOid() };
		qr.update(sql, params);

	}

	@Override
	public List<Orders> findAllOrders() throws Exception {
		String sql = "select * from orders order by ordertime desc";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		return queryRunner.query(sql, new BeanListHandler<Orders>(Orders.class));
	}

	@Override
	public List<Orders> findAllOrders(String state) throws Exception {
		String sql = "select * from orders where state = ? order by ordertime desc";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());		
		return queryRunner.query(sql, new BeanListHandler<Orders>(Orders.class), state);
	}
	

}
