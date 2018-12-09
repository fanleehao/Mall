package com.leehao.mall.web.servlet;

import com.leehao.mall.domain.Category;
import com.leehao.mall.service.CategoryService;
import com.leehao.mall.service.serviceImpl.CategoryServiceImpl;
import com.leehao.mall.utils.JedisUtils;
import com.leehao.mall.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
/**  1.使用频繁的访问数据库方法
 * 		//获取响应
		//service处理
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		//设置返回的json格式字符串-----，响应到客户端还是字符串()
		//“不能在网络上直接传输对象”，一般而言，resp.getWriter().write()只能传送字符文本text/html，resp.getWriter().print();还可以传送对象，最好指定编码方式
		String jsonStr = JSONArray.fromObject(list).toString();
		
		//告诉浏览器，本次响应的是json格式数据----如其他图片、mp3,方便ajax客户端得到响应后，会将json转为对象；而不是普通字符串
		resp.setContentType("text/html;charset=utf-8");   //浏览器默认,两种均可
//		resp.setContentType("application/json;charset=utf-8");
		//ajax服务端响应，不需要哦转发，会直接使用response响应回客户端
		//resp.getWriter().write(jsonStr);
		resp.getWriter().print(jsonStr);     //设置了返回的为application/json，当服务器响应的不是字符串时(如list),ajax将无法获取到回调的数据
*/
		
		/**
		 * 2.使用redis ---缓存技术
		 */
		Jedis jedis = JedisUtils.getJedis();
		String jsonstr = jedis.get("allCats");
		//未获取到
		if (null == jsonstr || "".equals(jsonstr)) {
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> list = categoryService.getAllCats();
			jsonstr = JSONArray.fromObject(list).toString();
			jedis.set("allCats", jsonstr);
//			System.out.println("weihuoqu");
		}
		//转发

		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().print(jsonstr);
		JedisUtils.closeJedis(jedis);
		
		return null;
	}
	public static void main(String[] args) {
		Jedis j = new Jedis("118.25.144.147", 6379);
		System.out.println(j.ping());
	}
}
