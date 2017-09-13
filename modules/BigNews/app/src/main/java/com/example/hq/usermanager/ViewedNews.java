package com.example.hq.usermanager;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by ThinkPad on 2017/9/11.
 */

public class ViewedNews extends SugarRecord {
    User    owner;
    Newsabs    data;
    public ViewedNews() {}
    public ViewedNews(User owner, Newsabs data) {
        this.owner = owner;
        this.data = data;
    }

    public   static void    view(User u, Newsabs data) {
        (new ViewedNews(u, data)).save();
    }

    public  static  boolean viewed(User u, Newsabs data) {
        List<ViewedNews> tmp = ViewedNews.find(ViewedNews.class, "owner = ? and data = ?", u.getId().toString(), data.getId().toString());
        return tmp.size() > 0;
    }

    public  void    unview(ViewedNews sn) {sn.delete();}

    public  static  void unview(User u, Newsabs data) {
        List<ViewedNews> tmp = ViewedNews.find(ViewedNews.class, "owner = ? and data = ?", u.getId().toString(), data.getId().toString());
        for (ViewedNews i: tmp) i.delete();
    }

    public  static  void clear(User u)
    {
        List<ViewedNews> tmp = ViewedNews.find(ViewedNews.class, "owner = ?", u.getId().toString());
        for (ViewedNews i: tmp) i.delete();
    }

    public   static List<ViewedNews>    getViewedNews(User u) {
        List<ViewedNews> tmp = ViewedNews.find(ViewedNews.class, "owner = ?", u.getId().toString());
        return tmp;
    }

    public Newsabs getData() {return data;}
}
