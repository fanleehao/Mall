package com.leehao.mall.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//2个属性3个方法
public class Cart {	
	//总计/积分
	private double total=0;
	//个数不确定的购物项 商品pid<===>CartItem
	Map<String,CartItem> items=new HashMap<String,CartItem>();
	
	//添加购物项到购物车
	//当用户点击加入购物车按钮,可以将当前要购买的商品id,商品数量发送到服务端,服务端根据商品id查询到商品信息
	//有了商品信息Product对象,有了要购买商品数量,当前的购物项也就可以获取到了
	public void addCartItemToCar(CartItem cartItem){
		//获取到正在想购物车中添加的商品pid
		String pid=cartItem.getProduct().getPid();
		
		//将当前的购物项加入购物车之前,判断之前是否买过这类商品
		//如果没有买过    list.add(cartItem);
		//如果买过: 获取到原先的数量,获取到本次的数量,相加之后设置到原先购物项上
		if(items.containsKey(pid)){
			//获取到原先的购物项
			CartItem oldItem=items.get(pid);
			oldItem.setNum(oldItem.getNum()+cartItem.getNum());
			
		}else{
			items.put(pid, cartItem);
		}
	}
	
	
	//返回MAP中所有的值
	public Collection<CartItem> getCartItems(){
		return items.values();
	}	
	
	//移除购物项
	public void removeCartItem(String pid){
		items.remove(pid);
	}
	//清空购物车
	public void clearCart(){
		items.clear();
	}
	
	//总计是可以经过计算获取到
	public double getTotal() {
		//向让总计请0
		total=0;
		//获取到Map中所有的购物项
		Collection<CartItem> values = items.values();
		
		//遍历所有的购物项,将购物项上的小计相加
		for (CartItem cartItem : values) {
			total+=cartItem.getSubTotal();
		}
		
		return total;
	}
	
	

	public void setTotal(double total) {
		this.total = total;
	}


	public Map<String, CartItem> getItems() {
		return items;
	}


	public void setItems(Map<String, CartItem> items) {
		this.items = items;
	}


}
