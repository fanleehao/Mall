package com.leehao.mall.dao.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.leehao.mall.dao.UserDao;
import com.leehao.mall.domain.User;
import com.leehao.mall.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		String statement = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		// 查詢器
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] param = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		queryRunner.update(statement, param);
	}

	@Override
	public User userActive(String code) throws SQLException {
		// 激活用户
		String sql = "select * from user where code = ?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		User user = queryRunner.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		// 更新数据库用户
		String uid = user.getUid();
		String sql = "update user set username=?, password=?, name=?, email=?, telephone=?, birthday=?, "
				+ "sex=?, state=?, code=? where uid=?";
		Object[] param = { user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),	user.getTelephone(), 
				user.getBirthday(), user.getSex(), user.getState(), user.getCode(), user.getUid()};
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, param);

	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
	}

	@Override
	public User findUserByUserName(String username) throws SQLException {
		String sql = "select * from user where username=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<User>(User.class),username);
	}

}
