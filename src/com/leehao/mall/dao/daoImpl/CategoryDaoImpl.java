package com.leehao.mall.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.leehao.mall.dao.CategoryDao;
import com.leehao.mall.domain.Category;
import com.leehao.mall.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCats() throws SQLException {
		// 调用dao
		String sql = "Select * from category";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		List<Category> list = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
//		System.out.println(list.size());
		return list;
	}

}
