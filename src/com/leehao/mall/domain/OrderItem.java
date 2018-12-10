package com.leehao.mall.domain;
/**
 * CREATE TABLE `orderitem` (
  `itemid` varchar(32) NOT NULL,
  `quantity` int(11) DEFAULT NULL,			#购买数量
  `total` double DEFAULT NULL,			#小计
  `pid` varchar(32) DEFAULT NULL,		#购买商品的id
  `oid` varchar(32) DEFAULT NULL,		#订单项所在订单id
  PRIMARY KEY (`itemid`),
  KEY `order_item_fk_0001` (`pid`),
  KEY `order_item_fk_0002` (`oid`),
  CONSTRAINT `order_item_fk_0001` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `order_item_fk_0002` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 * @author fanleehao
 *
 * 2018年12月10日
 */
public class OrderItem {
	private String itemid;
	private int quantity;
	private double total;
	private Product product;
	private Orders order;
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public OrderItem(String itemid, int quantity, double total, Product product, Orders order) {
		super();
		this.itemid = itemid;
		this.quantity = quantity;
		this.total = total;
		this.product = product;
		this.order = order;
	}
	public OrderItem() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", quantity=" + quantity + ", total=" + total + ", product=" + product
				+ ", order=" + order + "]";
	}
	
}
