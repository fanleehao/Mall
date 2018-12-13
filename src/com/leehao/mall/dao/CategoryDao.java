package com.leehao.mall.dao;

import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.domain.Category;

import jdk.nashorn.internal.runtime.ECMAErrors;

public interface CategoryDao {

	List<Category> getAllCats() throws SQLException;

	void addCategory(Category category) throws Exception;

	Category findCategoryById(String cid) throws Exception;

	void editCategory(Category category)throws Exception;

	void deleteCategoryById(String cid) throws Exception;

}
