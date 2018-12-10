package com.leehao.mall.test;

import java.util.ArrayList;

import org.junit.Test;

import com.leehao.mall.domain.User;

public class TestOrderItem {
	@Test
	public void demo1() {
		ArrayList<User> arrayList = new ArrayList<>();
		User user = new User();
		user.setName("qqqq");
		arrayList.add(user);
		user.setName("bbb");
		arrayList.add(user);
		
		for (User u : arrayList) {
			System.out.println(u);
		}
	}
}
