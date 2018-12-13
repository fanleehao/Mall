package com.leehao.mall.service;

import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.domain.Category;

public interface CategoryService {

	List<Category> getAllCats() throws SQLException;

	void addCategory(Category category)throws Exception;

	Category findCategoryById(String cid) throws Exception;

	void editCategory(Category category)throws Exception;

	void deleteCategoryById(String cid)throws Exception;

}