package com.leehao.mall.service;

import java.sql.SQLException;
import java.util.List;

import com.leehao.mall.domain.Category;

public interface CategoryService {

	List<Category> getAllCats() throws SQLException;

}