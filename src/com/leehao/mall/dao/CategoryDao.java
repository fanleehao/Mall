package com.leehao.mall.dao;

import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.domain.Category;

public interface CategoryDao {

	List<Category> getAllCats() throws SQLException;

}
