package com.leehao.mall.service.serviceImpl;

import java.sql.SQLException;
import java.util.Date;

import javax.management.RuntimeErrorException;

import com.leehao.mall.dao.UserDao;
import com.leehao.mall.dao.daoImpl.UserDaoImpl;
import com.leehao.mall.domain.User;
import com.leehao.mall.service.UserService;
import com.leehao.mall.utils.BeanFactory;

public class UserServiceImpl implements UserService {
	UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
	@Override
	public void userRegist(User user) throws SQLException {
		// 实现注册----调用dao		
		userDao.userRegist(user);

	}

	@Override
	public boolean userActive(String code) throws SQLException {
		// 返回通过select * from user where code = ?的查询结果，如果查找失败，就返回false
		User user = userDao.userActive(code);
		if (null != user) {
			// 可以查到当前code的用户,修改状态码，删除激活码
			user.setState(1);
			user.setCode(null);
			// 更新数据库,update user set state=1,code=null where
			// uid=?-------可以但不便于拓展
			// update user set username=?, password=?, name=?, email=?,
			// telephone=?,birthday=?, sex=?,
			// state=1, code=null where uid=?
			userDao.updateUser(user);

			return true;
		} else {
			return false;
		}
	}

	@Override
	public User userLogin(User user) throws SQLException {		

		// 登录成功,查询数据库select * from user where username=?,password=?
		User uu = userDao.userLogin(user);
		if(null == uu){
			throw new RuntimeException("密码错误！");
		}else if (uu.getState()==0) {
			throw new RuntimeException("账号未激活！");
		}else{
			return uu;
		}
	}

	@Override
	public User findUserByUserName(String username) throws SQLException {
		User user = userDao.findUserByUserName(username);
		return user;
	}

}
