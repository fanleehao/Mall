package com.leehao.mall.web.servlet;

import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leehao.mall.domain.Cart;
import com.leehao.mall.domain.CartItem;
import com.leehao.mall.domain.OrderItem;
import com.leehao.mall.domain.Orders;
import com.leehao.mall.domain.PageModel;
import com.leehao.mall.domain.User;
import com.leehao.mall.service.OrderService;
import com.leehao.mall.service.serviceImpl.OrderServiceImpl;
import com.leehao.mall.utils.PaymentUtil;
import com.leehao.mall.utils.UUIDUtils;
import com.leehao.mall.web.base.BaseServlet;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {

	public String addOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取订单-----将购物车的中数据提交
		// 1..确认用户已登录
		User user = (User) req.getSession().getAttribute("loginUser");
		if (null == user) {
			// 未登录
			req.setAttribute("msg", "请登录之后再下单");
			return "/jsp/login.jsp";
		}
		// 获取购物车
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		// 构造订单
		Orders order = new Orders();
		order.setOid(UUIDUtils.getId());
		order.setOrdertime(new Date());
		order.setState(1);
		order.setTotal(cart.getTotal());
		order.setUser(user);
		// System.out.println(order);
		// 构造订单项
		// OrderItem orderItem = new OrderItem();
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getId());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getNum());
			orderItem.setOrder(order);
			orderItem.setTotal(item.getSubTotal());
			// 将每一个明细添加到订单中
			order.getOrderItems().add(orderItem);

		}

		// 调用service，添加订单
		OrderService orderService = new OrderServiceImpl();
		orderService.addOrder(order);
		//
		// //清空购物车，转发
		cart.clearCart();
		req.setAttribute("order", order);

		return "/jsp/order_info.jsp";
	}

	// 查询所有订单
	public String findMyOrdersWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取用户
		User user = (User) req.getSession().getAttribute("loginUser");
		// 获取当前页
		int curPageNum = Integer.parseInt(req.getParameter("num"));
		// 调用业务层，实现功能，返回一个pageModel对象
		OrderService orderService = new OrderServiceImpl();
		PageModel pageModel = orderService.findMyOrdersWithPage(user, curPageNum);

		// 将pageModel对象查到的结果，放到requset中，转发
		req.setAttribute("pageModel", pageModel);
		return "/jsp/order_list.jsp";
	}

	// findOrderByOid
	public String findOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取到订单oid
		String oid = req.getParameter("oid");
		// 调用业务层功能:根据订单编号查询订单信息
		OrderService OrderService = new OrderServiceImpl();
		Orders order = OrderService.findOrderByOid(oid);
		// 将订单放入request
		req.setAttribute("order", order);
		// 转发到/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}

	// payOrder
	public String payOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 1. 获取参数，更新订单信息
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String oid = req.getParameter("oid");
		String telephone = req.getParameter("telephone");
		String pd_FrpId = req.getParameter("pd_FrpId");
		OrderService orderService = new OrderServiceImpl();
		Orders order = orderService.findOrderByOid(oid);
		order.setName(name);
		order.setAddress(address);
		order.setTelephone(telephone);

		orderService.updateOrder(order);

		// 2. 发送第三方请求的加密以及密文，等待响应
		// 把付款所需要的参数准备好:
		String p0_Cmd = "Buy";
		// 商户编号
		String p1_MerId = "10001126856";
		// 订单编号
		String p2_Order = oid;
		// 金额
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 接受响应参数的Servlet
		String p8_Url = "http://localhost:8080/Mall/OrderServlet?method=callBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 公司的秘钥
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		// 调用易宝的加密算法,对所有数据进行加密,返回电子签名
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		// System.out.println(sb.toString());
		// 使用重定向：
		resp.sendRedirect(sb.toString());

		return null;
	}

	// 接受第三方的支付返回结果----回调
	public String callBack(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//
		// 接收易宝支付的数据
		// 验证请求来源和数据有效性
		// 阅读支付结果参数说明
		// System.out.println("==============================================");
		String p1_MerId = req.getParameter("p1_MerId");
		String r0_Cmd = req.getParameter("r0_Cmd");
		String r1_Code = req.getParameter("r1_Code");
		String r2_TrxId = req.getParameter("r2_TrxId");
		String r3_Amt = req.getParameter("r3_Amt");
		String r4_Cur = req.getParameter("r4_Cur");
		String r5_Pid = req.getParameter("r5_Pid");
		String r6_Order = req.getParameter("r6_Order");
		String r7_Uid = req.getParameter("r7_Uid");
		String r8_MP = req.getParameter("r8_MP");
		String r9_BType = req.getParameter("r9_BType");
		String rb_BankId = req.getParameter("rb_BankId");
		String ro_BankOrderId = req.getParameter("ro_BankOrderId");
		String rp_PayDate = req.getParameter("rp_PayDate");
		String rq_CardNo = req.getParameter("rq_CardNo");
		String ru_Trxtime = req.getParameter("ru_Trxtime");

		// hmac
		String hmac = req.getParameter("hmac");
		// 利用本地密钥和加密算法 加密数据
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

		// 保证数据合法性
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向

				// 如果支付成功,更新订单状态
				OrderService OrderService = new OrderServiceImpl();
				Orders order = OrderService.findOrderByOid(r6_Order);
				order.setState(2);
				OrderService.updateOrder(order);
				// 向request放入提示信息
				req.setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
				// 转发到/jsp/info.jsp
				return "/jsp/info.jsp";

			} else if (r9_BType.equals("2")) {
				// 修改订单状态:
				// 服务器点对点，来自于易宝的通知
				System.out.println("收到易宝通知，修改订单状态！");//
				// 回复给易宝success，如果不回复，易宝会一直通知
				resp.getWriter().print("success");
			}

		} else {
			throw new RuntimeException("数据被篡改！");
		}
		return null;

	}

}
