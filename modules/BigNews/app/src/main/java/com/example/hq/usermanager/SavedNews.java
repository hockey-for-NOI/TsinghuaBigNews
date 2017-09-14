package com.example.hq.usermanager;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2017/9/11.
 */

public class SavedNews extends SugarRecord {
    User    owner;
    Newsdata    data;
    Newsabs abs;
    public SavedNews() {}
    public SavedNews(User owner, Newsdata data) {
        this.owner = owner;
        this.data = data;
        List<Newsabs> tmp = Newsabs.find(Newsabs.class, "newsid = ?", data.getNewsID());
        abs = tmp.get(0);
    }

    public   static void    save(User u, Newsdata data) {
        (new SavedNews(u, data)).save();
    }

    public  static  boolean exist(User u, Newsdata data) {
        List<SavedNews> tmp = SavedNews.find(SavedNews.class, "owner = ? and data = ?", u.getId().toString(), data.getId().toString());
        return tmp.size() > 0;
    }

    public  void    unsave(SavedNews sn) {sn.delete();}

    public  static  void unsave(User u, Newsdata data) {
        List<SavedNews> tmp = SavedNews.find(SavedNews.class, "owner = ? and data = ?", u.getId().toString(), data.getId().toString());
        for (SavedNews i: tmp) i.delete();
    }

    public  static  void clear(User u)
    {
        List<SavedNews> tmp = SavedNews.find(SavedNews.class, "owner = ?", u.getId().toString());
        for (SavedNews i: tmp) i.delete();
    }

    public   static List<SavedNews>    getSavedNews(User u) {
        List<SavedNews> tmp = SavedNews.find(SavedNews.class, "owner = ?", u.getId().toString());
        return tmp;
    }

    public   static ArrayList<Newsabs>    getSavedNewsabs(User u) {
        List<SavedNews> tmp = SavedNews.find(SavedNews.class, "owner = ?", u.getId().toString());
        ArrayList<Newsabs> res = new ArrayList<Newsabs>();
        for (SavedNews i: tmp) res.add(i.abs);
        return res;
    }

    public Newsdata getData() {return data;}
}
