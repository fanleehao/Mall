package com.leehao.mall.domain;

/**
 * 
 * @author fanleehao -- 2.1 创建分类表 CREATE TABLE `category` ( `cid` varchar(32)
 *         NOT NULL, `cname` varchar(20) DEFAULT NULL, #分类名称 PRIMARY KEY (`cid`)
 *         ) ENGINE=InnoDB DEFAULT CHARSET=utf8; 2018年12月7日
 */
public class Category {
	private String cid;
	private String cname;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Category() {
		
	}
	
	public Category(String cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}
	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cname=" + cname + "]";
	}
	
	
}
