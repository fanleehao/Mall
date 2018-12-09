package com.leehao.mall.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.leehao.mall.dao.ProductDao;
import com.leehao.mall.domain.Product;
import com.leehao.mall.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findNews() throws SQLException {
		//pflag=0,有货
		String sql = "Select * from product where pflag=0 order by pdate desc limit 0, 9";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findHots() throws SQLException {
		//pflag=0,有货, is_hot=1最热
		String sql = "Select * from product where pflag=0 and is_hot=1 order by pdate desc limit 0, 9";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product finProductById(String pid) throws Exception {
		String sql = "Select * from product where pid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

}
