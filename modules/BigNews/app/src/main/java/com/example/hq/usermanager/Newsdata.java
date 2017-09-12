package com.example.hq.usermanager;

import java.util.ArrayList;
import java.util.List;

import com.example.sth.net.NewsAPI;
import com.example.sth.net.NewsParam;
import com.orm.SugarRecord;

import org.json.JSONObject;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class Newsdata extends SugarRecord {
    String newsid;
    String jsonstr;
    String display;

    public String   getNewsID() {return newsid;}

    public Newsdata() {}

    public Newsdata(String newsid, String jsonstr) {
        this.newsid = newsid;
        this.jsonstr = jsonstr;
        try {
            JSONObject jobj = new JSONObject(jsonstr);
            this.display = jobj.getString("news_Category") + "\n" +
                    jobj.getString("news_Title") + "\n" +
                    jobj.getString("news_Content") + "\n" +
                    jobj.getString("news_Pictures") + "\n" +
                    jobj.getString("news_Journal") + "\n" +
                    jobj.getString("news_Time") + "\n" +
                    "";
        }
        catch (Exception e) {this.display = "Waiting...";}
    }

    public  static  void    grab(String newsid) {
        List<Newsdata> tmp = Newsdata.find(Newsdata.class, "newsid = ?", newsid);
        if (tmp.size() > 0) return;
        try {
            String detail = NewsAPI.getNews(new NewsParam().setID(newsid));
            JSONObject obj = new JSONObject(detail);
            Newsdata data = new Newsdata(newsid, detail);
            data.save();
        }
        catch (Exception e) {
        }
    }

    public  static  Newsdata    get(String newsid) {
        List<Newsdata> tmp = Newsdata.find(Newsdata.class, "newsid = ?", newsid);
        if (tmp.size() > 0) return tmp.get(0); else return new Newsdata("", "");
    }

    public  String  getDisplay() {return display;}
}