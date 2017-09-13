package com.example.hq.usermanager;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by ThinkPad on 2017/9/11.
 */

public class SavedNews extends SugarRecord {
    User    owner;
    Newsdata    data;
    public SavedNews() {}
    public SavedNews(User owner, Newsdata data) {
        this.owner = owner;
        this.data = data;
    }

    public   static void    save(User u, Newsdata data) {
        (new SavedNews(u, data)).save();
    }

    public  void    unsave(SavedNews sn) {sn.delete();}

    public  static  void clear(User u)
    {
        List<SavedNews> tmp = SavedNews.find(SavedNews.class, "owner = ?", u.getId().toString());
        for (SavedNews i: tmp) i.delete();
    }

    public   static List<SavedNews>    getSavedNews(User u) {
        List<SavedNews> tmp = SavedNews.find(SavedNews.class, "owner = ?", u.getId().toString());
        return tmp;
    }

    public Newsdata getData() {return data;}
}
