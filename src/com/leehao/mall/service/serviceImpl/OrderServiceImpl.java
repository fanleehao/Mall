package com.leehao.mall.service.serviceImpl;

import java.sql.Connection;
import java.util.List;

import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;
import com.leehao.mall.domain.PageModel;
import com.leehao.mall.domain.User;
import com.leehao.mall.service.OrderDao;
import com.leehao.mall.service.OrderService;
import com.leehao.mall.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {
	OrderDao orderDao = new OrderDaoImpl();

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
//		}finally {        ----从连接池中获取，多次关闭就没有了
//			//释放链接
//			conn.close();
//		}
		
		
	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curPageNum) throws Exception {
		//调用DAO层，构造pageModel对象，
		
		int totalRecords = orderDao.findAllRecords(user);
		// 1.分页参数
		PageModel pageModel = new PageModel(curPageNum, totalRecords, 3);   //3是每页的个数
		// 2.关联当前页要显示的对象集合list,,,对每个订单，应该还包含其中的所有明细项
		// select * from orders where uid = ? limit ?,?
		List orders = orderDao.findMyOrdersWithPage(user, pageModel.getStartIndex(), pageModel.getPageSize());
		pageModel.setList(orders);
		
		// 3.关联URL
		pageModel.setUrl("/OrderServlet?method=findMyOrdersWithPage");
		
		return pageModel;
	}

	@Override
	public Orders findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Orders order) throws Exception {
		// TODO Auto-generated method stub
		orderDao.updateOrder(order);
	}
	
}
