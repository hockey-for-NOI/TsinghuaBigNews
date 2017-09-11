package com.example.hq.usermanager;

import com.example.sth.net.NewsAPI;
import com.example.sth.net.NewsParam;
import com.orm.SugarRecord;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class Newsabs extends SugarRecord {
    User owner;
    String jsonstr;
    String category;
    String title;
    String content;

    public Newsabs() {
    }

    public Newsabs(User owner, String jsonstr) {
        this.owner = owner;
        this.jsonstr = jsonstr;

        try {
            JSONObject obj = new JSONObject(jsonstr);
            this.category = obj.getString("newsClassTag");
            this.title = obj.getString("news_Title");
            this.content = obj.getString("news_Intro");
        }
        catch (Exception e) {
            this.category = "";
            this.title = "";
            this.content = "";
        }
    }

    public static ArrayList<Newsabs> userGrab(User owner, NewsParam params){
        String grabRes = "";
        try {
            grabRes = NewsAPI.getNews(params);
        }
        catch (IOException e) {
            //throw new NewsGrabError();
        }
        ArrayList<Newsabs> result = new ArrayList<Newsabs>();
        try {
            JSONObject obj = new JSONObject(grabRes);
            JSONArray arr = obj.optJSONArray("list");
            for (int i=0; i<arr.length(); i++) {
                JSONObject subObj = arr.getJSONObject(i);
                Newsabs convAbs = new Newsabs(owner, subObj.toString());
                result.add(convAbs);
            }
       }
        catch (Exception e) {
            result = new ArrayList<Newsabs>();
        }
        finally {
            for (Newsabs i: result) i.save();
            return result;
        }
    }

    public static   ArrayList<Newsabs> getCachedAbstract(User owner) {
        List<Newsabs> lis = Newsabs.find(Newsabs.class, "owner = ?", owner.getId().toString());
        ArrayList<Newsabs> alis = new ArrayList<Newsabs>(lis);
        return alis;
    }

    public static   ArrayList<Newsabs> getCachedAbstractByCategory(User owner, String category) {
        List<Newsabs> lis = Newsabs.find(Newsabs.class, "owner = ? and category = ?", owner.getId().toString(), category);
        ArrayList<Newsabs> alis = new ArrayList<Newsabs>(lis);
        return alis;
    }

    public static   int clearCachedAbstract(User owner) {
        List<Newsabs> lis = Newsabs.find(Newsabs.class, "owner = ?", owner.getId().toString());
        for (Newsabs i: lis) i.delete();
        return lis.size();
    }

    public String getTitle() {return title;}
    public String getContent() {return content;}

    public String getAbs() {return title + ":" + content;}
}
