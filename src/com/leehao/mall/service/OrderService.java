package com.leehao.mall.service;

import com.leehao.mall.domain.Orders;
import com.leehao.mall.domain.PageModel;
import com.leehao.mall.domain.User;

public interface OrderService {

	void addOrder(Orders order)throws Exception;

	PageModel findMyOrdersWithPage(User user, int curPageNum) throws Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void updateOrder(Orders order) throws Exception;

}
