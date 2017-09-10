package com.example.sth.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

//TODO  Exception

//example:
//NewsAPI.getNews(new NewsParam().setID("201601220712e3081db0bde94949a0e0b1ccd49f1343")); //search by ID
//NewsAPI.getNews(new NewsParam().setCategory(1).setPageSize(10));  //search latest technology news, show 10 pieces of news per page
//String s = NewsAPI.getNews(new NewsParam().setKeyword("江泽民")); //search by keyword

public class NewsAPI {
	
	//see parameter details in NewsParam 
	public static String getNews(NewsParam p) throws MalformedURLException,IOException {
		if (p.ID.length() != 0)
			return searchByID(p.ID);
		
		if (p.keyword.length() != 0)
			return search(p);
		
		return getLatestNews(p);
	}
	
//-----------------------------------------------------------------------------------------------------------------------
	private static String searchByID(String ID) throws MalformedURLException,IOException {
		String url = "http://166.111.68.66:2042/news/action/query/detail?newsId=" + ID;
		return getString(url);
	}
	
//-----------------------------------------------------------------------------------------------------------------------
	
	private static String getLatestNews(NewsParam p) throws MalformedURLException,IOException {
		String url = "http://166.111.68.66:2042/news/action/query/latest?pageNo=" + p.pageNo + "&pageSize=" + p.pageSize;
		if (p.category != -1)
			url = url + "&category=" + p.category;
		return getString(url);
	}
	
//------------------------------------------------------------------------------------------------------------------------
	
	private static String search(NewsParam p) throws MalformedURLException,IOException {
		String url = "http://166.111.68.66:2042/news/action/query/search?keyword=" + p.keyword + "&pageNo=" + p.pageNo + "&pageSize=" + p.pageSize;
		if (p.category != -1)
			url = url + "&category=" + p.category;
		return getString(url);
	}
	
//-----------------------------------------------------------------------------------------------------------------------
	
	private static String getString(String urlString) throws MalformedURLException,IOException {
		URL url = new URL(urlString);
		BufferedReader in = new BufferedReader(new
				InputStreamReader(url.openStream()));
		String s = in.readLine();
		return s;
	}
	
}