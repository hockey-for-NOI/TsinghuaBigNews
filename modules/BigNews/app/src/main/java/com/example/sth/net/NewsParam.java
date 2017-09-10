package com.example.sth.net;

//pageNo <= pageSize !!!!!

public class NewsParam {
	public String keyword, ID;
	public int category, pageNo, pageSize;
	public NewsParam(){
		ID = "";       //if you set ID, then other parameters will not work
		keyword = "";  //search the latest news without keyword
		category = -1; //all categories
		               //pageNo <= pageSize
		pageNo = 1;    //start pageNumber is 1, not 0
		pageSize = 20; //the max pageSize is 500
	}
	public NewsParam setID(String _ID) { this.ID = _ID; return this;}
	public NewsParam setKeyword(String _keyword) { this.keyword = _keyword; return this;}
	public NewsParam setCategory(int _category) { this.category = _category; return this;}
	public NewsParam setPageNo(int _pageNo) { this.pageNo = _pageNo; return this;}
	public NewsParam setPageSize(int _pageSize) { this.pageSize = _pageSize; return this;}
	
}
