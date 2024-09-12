package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.User;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User userVO = (User)session.getAttribute("user");
		SearchVO searchVO = new SearchVO();
		
		int page = 1;
		if (request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		String menu = request.getParameter("menu");
		
		PurchaseService service = new PurchaseServiceImpl();
		Map<String,Object> map = service.getPurchaseList(searchVO, userVO.getUserId());
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("menu",menu);
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
