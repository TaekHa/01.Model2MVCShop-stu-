package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductVO findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, p.price, p.image_file, p.reg_date, t.tran_status_code FROM product p, transaction t WHERE p.prod_no = t.prod_no(+) AND p.prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			if(rs.getString("tran_status_code") != null) {
				productVO.setProTranCode(rs.getString("tran_status_code"));
			}else {
				productVO.setProTranCode("0");
			}
			
		}
		
		con.close();
		
		
		return productVO;
	}
	
	public Map<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, p.price, p.image_file, p.reg_date, t.tran_status_code FROM product p, transaction t WHERE p.prod_no = t.prod_no(+)";
		if (searchVO.getSearchCondition() != null) {
			if(searchVO.getSearchCondition().equals("0")) {
				sql +=" AND prod_no LIKE '%" + searchVO.getSearchKeyword()
				+"%'";				
			} else if(searchVO.getSearchCondition().equals("1")) {
				sql +=" AND prod_name LIKE '%" + searchVO.getSearchKeyword()
				+"%'";
			} else if (searchVO.getSearchCondition().equals("2")) {
				sql += " AND price LIKE'" + searchVO.getSearchKeyword()
				+"'";
			}
		}
		sql += " ORDER BY prod_no";
		
		PreparedStatement stmt = 
				con.prepareStatement(sql , ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수: "+total);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("count",new Integer(total));
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage() : " + searchVO.getPage());
		System.out.println("searchVO.getPageUnit()" + searchVO.getPageUnit());
		
		List<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				vo.setProTranCode(rs.getString("tran_status_code"));
				
				list.add(vo);
				if(!rs.next()) {
					break;
				}				
			}
		}
		System.out.println("list.size() : "+list.size());
		map.put("list", list);
		System.out.println("map().size() : "+map.size());
		
		con.close();
		
		return map;
	}
	
	public void insertProduct(ProductVO productVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
	 
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().split("-")[0]+productVO.getManuDate().split("-")[1]+productVO.getManuDate().split("-")[2]);
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public void updateProduct(ProductVO productVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE product SET prod_name=?,prod_detail=?,price=?,image_file=? WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1,productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setInt(3, productVO.getPrice());
		stmt.setString(4, productVO.getFileName());
		stmt.setInt(5, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
		
	}
}
