package com.leehao.mall.service;

import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.domain.Product;

public interface ProductService {

	List<Product> findNews() throws SQLException;

	List<Product> findHots() throws SQLException;

	Product finProductById(String pid) throws Exception;

}
