package com.model2.mvc.common;


public class SearchVO {
	
	private int page; //보고 있는 페이지
	String searchCondition;
	String searchKeyword;
	int pageUnit; //검색한 page에 출력되는 Row 수
	int indexUnit;//페이지수
	
	public SearchVO(){
	}
	
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getIndexUnit() {
		return indexUnit;
	}

	public void setIndexUnit(int indexUnit) {
		this.indexUnit = indexUnit;
	}
}