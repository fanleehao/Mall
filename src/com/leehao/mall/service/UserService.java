package com.leehao.mall.service;

import java.sql.SQLException;

import com.leehao.mall.domain.User;

public interface UserService {

	void userRegist(User user) throws SQLException;

	boolean userActive(String code) throws SQLException;

	User userLogin(User user)throws SQLException;

	User findUserByUserName(String username) throws SQLException;

}
