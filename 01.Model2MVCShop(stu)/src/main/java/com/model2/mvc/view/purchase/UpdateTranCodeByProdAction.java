package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		int tranCode=Integer.parseInt(request.getParameter("tranCode"));
		int currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		System.out.println(prodNo+":"+tranCode+":"+currentPage);
		
		SearchVO searchVO = new SearchVO();
		
		searchVO.setPage(currentPage);
		
		
		PurchaseService service = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = service.getPurchaseByProd(prodNo);
		System.out.println(purchaseVO);
		
		service.updateTranCode(purchaseVO, tranCode);
		request.setAttribute("searchVO", searchVO);

		
		return "redirect:/listProduct.do?menu=manage";
	}

}
