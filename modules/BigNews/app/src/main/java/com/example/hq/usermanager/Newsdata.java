package com.example.hq.usermanager;

import java.util.ArrayList;
import java.util.Arrays;
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
    String category;
    String title;
    String content;
    String pictures;
    String source;
    String journal;
    String author;
    String time;
    boolean complete;

    public String   getTitle() {return title;}
    public String   getCategory() {return category;}
    public String   getContent() {return content;}
    public String   getSource() {return source;}
    public String   getJournal() {return journal;}
    public String   getAuthor() {return author;}
    public String   getTime() {return time;}
    public ArrayList<String> getPictures() {
        return pictures.length() > 0 ? new ArrayList<String>(
                Arrays.asList(pictures.split(";"))) : new ArrayList<String>();
    }
    public boolean  isComplete() {return complete;}
    public String   getNewsID() {return newsid;}

    public Newsdata() {}

    public Newsdata(String newsid, String jsonstr) {
        this.newsid = newsid;
        this.jsonstr = jsonstr;
        try {
            JSONObject jobj = new JSONObject(jsonstr);
            this.category = jobj.getString("news_Category");
            this.title = jobj.getString("news_Title");
            this.content = jobj.getString("news_Content");
            this.pictures = jobj.getString("news_Pictures");
            this.journal = jobj.getString("news_Journal");
            this.time = jobj.getString("news_Time");
            this.source = jobj.getString("news_Source");
            this.author = jobj.getString("news_Author");
            this.complete = true;
        }
        catch (Exception e) {this.complete = false;}
    }

    public  static  void    grab(String newsid) {
        List<Newsdata> tmp = Newsdata.find(Newsdata.class, "newsid = ?", newsid);
        if (tmp.size() > 0 && tmp.get(0).complete) return;
        try {
            for (Newsdata i: tmp) i.delete();
            String detail = NewsAPI.getNews(new NewsParam().setID(newsid));
            JSONObject obj = new JSONObject(detail);
            Newsdata data = new Newsdata(newsid, detail);
            data.save();
            Phrase.pourPhrase(data);
        }
        catch (Exception e) {
        }
    }

    public  static  Newsdata    get(String newsid) {
        List<Newsdata> tmp = Newsdata.find(Newsdata.class, "newsid = ?", newsid);
        if (tmp.size() > 0) return tmp.get(0); else return new Newsdata("", "");
    }

    public  Newsabs getAbs() {
        List<Newsabs> tmp = Newsabs.find(Newsabs.class, "newsid = ?", getNewsID());
        return tmp.get(0);
    }
}