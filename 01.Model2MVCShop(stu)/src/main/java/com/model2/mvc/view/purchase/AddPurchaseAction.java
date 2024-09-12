package com.model2.mvc.view.purchase;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class AddPurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		ProductService productService = new ProductServiceImpl();
		UserService userService = new UserServiceImpl();
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		PurchaseVO purchaseVO=new PurchaseVO();
		purchaseVO.setPurchaseProd(productService.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setBuyer(userService.getUser(request.getParameter("buyerId")));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		if(request.getParameter("receiverDate") != null && request.getParameter("receiverDate").trim() != "") {
			purchaseVO.setDivyDate(Date.valueOf(request.getParameter("receiverDate")));
		}else {
			purchaseVO.setDivyDate(null);
		}
		
		purchaseService.addPurchase(purchaseVO);
		System.out.println(purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/addPurchase.jsp";
	}
}