package com.leehao.mall.service.serviceImpl;

import java.sql.Connection;

import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;
import com.leehao.mall.service.OrderDao;
import com.leehao.mall.service.OrderService;
import com.leehao.mall.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void addOrder(Orders order) throws Exception {
		//保存订单和订单下所有的订单项(同时成功,失败)
				/*try {
					JDBCUtils.startTransaction();
					OrderDao orderDao=new OrderDaoImp();
					orderDao.saveOrder(order);
					for(OrderItem item:order.getList()){
						orderDao.saveOrderItem(item);
					}
					JDBCUtils.commitAndClose();
				} catch (Exception e) {
					JDBCUtils.rollbackAndClose();
				}
				*/
		OrderDao orderDao = new OrderDaoImpl();
		//调用Dao，添加订单--------通过事务进行控制
		Connection conn = null;
		try {
			//获取链接
			conn = JDBCUtils.getConnection();
			//开启事物
			conn.setAutoCommit(false);
			//保存订单
			orderDao.addOrder(conn, order);
			//保存所有订单项
			for (OrderItem item : order.getOrderItems()) {
				orderDao.addOrderItems(conn, item);
			}
			//提交
			conn.commit();
		} catch (Exception e) {
			//失败则回滚
			conn.rollback();
		}
//		}finally {
//			//释放链接
//			conn.close();
//		}
		
		
	}

}
