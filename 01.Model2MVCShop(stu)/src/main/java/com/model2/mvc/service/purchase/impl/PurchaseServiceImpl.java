package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	//Field
	private PurchaseDAO purchaseDAO;
	
	//Constructor
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}
	
	//method
	@Override
	public void addPurchase(PurchaseVO purchaseVO) throws Exception {
		// TODO Auto-generated method stub
		purchaseDAO.insertPurchase(purchaseVO);
	}

	@Override
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.findPurchase(tranNo);
	}
	
	@Override
	public PurchaseVO getPurchaseByProd(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.findPurchaseByProd(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(SearchVO searchVO, String userId) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.getPurchaseList(searchVO, userId);
	}

	@Override
	public Map<String, Object> getSaleList(SearchVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.getSaleList(searchVO);
	}

	@Override
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		// TODO Auto-generated method stub
		purchaseDAO.updatePurchase(purchaseVO);
	}

	@Override
	public void updateTranCode(PurchaseVO purchaseVO, int tranStatusCode) throws Exception {
		// TODO Auto-generated method stub
		purchaseDAO.updateTranCode(purchaseVO, tranStatusCode);
	}

}
