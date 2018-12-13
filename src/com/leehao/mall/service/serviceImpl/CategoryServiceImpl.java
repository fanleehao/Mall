package com.leehao.mall.service.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JDesktopPane;

import com.leehao.mall.dao.CategoryDao;
import com.leehao.mall.dao.daoImpl.CategoryDaoImpl;
import com.leehao.mall.domain.Category;
import com.leehao.mall.service.CategoryService;
import com.leehao.mall.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public List<Category> getAllCats() throws SQLException {
		// servic層
		List<Category> list = categoryDao.getAllCats();
		return list;
	}

	@Override
	public void addCategory(Category category) throws Exception {
		// 添加到数据库
		categoryDao.addCategory(category);
		// 修改redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);

	}

	@Override
	public Category findCategoryById(String cid) throws Exception {
		return categoryDao.findCategoryById(cid);
	}

	@Override
	public void editCategory(Category category) throws Exception {
		// 修改数据库
		categoryDao.editCategory(category);
		// 清空redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

	@Override
	public void deleteCategoryById(String cid) throws Exception {
		// 修改数据库
		categoryDao.deleteCategoryById(cid);
		// 清空redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);

	}

}
