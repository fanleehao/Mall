package com.leehao.mall.dao;

import java.sql.SQLException;

import com.leehao.mall.domain.User;

public interface UserDao {

	void userRegist(User user) throws SQLException;

	User userActive(String code) throws SQLException;

	void updateUser(User user) throws SQLException;

	User userLogin(User user)throws SQLException;

	User findUserByUserName(String username) throws SQLException;

}
