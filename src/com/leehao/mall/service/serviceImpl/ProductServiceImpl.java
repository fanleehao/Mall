package com.leehao.mall.service.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.dao.ProductDao;
import com.leehao.mall.dao.daoImpl.ProductDaoImpl;
import com.leehao.mall.domain.Product;
import com.leehao.mall.service.ProductService;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao = new ProductDaoImpl();
	
	@Override
	public List<Product> findNews() throws SQLException {
		List<Product> newsProductList = productDao.findNews();
		return newsProductList;
	}

	@Override
	public List<Product> findHots() throws SQLException {
		List<Product> hotProductList = productDao.findHots();
		return hotProductList;
	}

	@Override
	public Product finProductById(String pid) throws Exception {
		return productDao.finProductById(pid);		
	}

}
