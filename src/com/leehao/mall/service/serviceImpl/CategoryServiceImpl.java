package com.leehao.mall.service.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.dao.CategoryDao;
import com.leehao.mall.dao.daoImpl.CategoryDaoImpl;
import com.leehao.mall.domain.Category;
import com.leehao.mall.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> getAllCats() throws SQLException {
		// servic層
		CategoryDao categoryDao = new CategoryDaoImpl();
		List<Category> list = categoryDao.getAllCats();		
		return list;
	}

}
