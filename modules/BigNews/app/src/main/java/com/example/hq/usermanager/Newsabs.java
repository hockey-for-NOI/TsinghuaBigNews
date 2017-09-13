package com.example.hq.usermanager;

import com.example.sth.net.NewsAPI;
import com.example.sth.net.NewsParam;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.json.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class Newsabs extends SugarRecord {
    String jsonstr;
    String category;
    String title;
    String content;
    String newsid;
    String pictures;
    long time;

    public Newsabs() {
    }

    public Newsabs(String jsonstr) {
        this.jsonstr = jsonstr;

        try {
            JSONObject obj = new JSONObject(jsonstr);
            this.category = obj.getString("newsClassTag");
            this.title = obj.getString("news_Title");
            this.content = obj.getString("news_Intro");
            this.newsid = obj.getString("news_ID");
            this.time = (new SimpleDateFormat("yyyyMMddHHmmss")).parse(obj.getString("news_Time")).getTime();
            this.pictures = obj.getString("news_Pictures");
        }
        catch (Exception e) {
            this.category = "";
            this.title = "";
            this.content = "";
            this.newsid = "";
            this.pictures = "";
            this.time = 0;
        }
    }

    public  long    getTime() {return time;}

    public static ArrayList<Newsabs> grab(NewsParam params){
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
                Newsabs convAbs = new Newsabs(subObj.toString());
                if (Newsabs.find(Newsabs.class, "newsid = ?", convAbs.newsid).size() > 0) continue;
                result.add(convAbs); convAbs.save();
            }
       }
        catch (Exception e) {
            result = new ArrayList<Newsabs>();
        }
        finally {
            return result;
        }
    }

    public static   ArrayList<Newsabs> getCachedAbstractByCategory(String category) {
        if (0 == category.compareTo("推荐")) return getCachedAbstractByAdvice();
        List<Newsabs> lis = Newsabs.find(Newsabs.class, "category = ?", category);
        ArrayList<Newsabs> alis = new ArrayList<Newsabs>();
        for (Newsabs i: lis) {
            boolean flag = true;
            for (String t: User.getStopList())
                if (i.jsonstr.contains(t)) flag = false;
            if (flag) alis.add(i);
        }
        Collections.sort(alis, new Comparator<Newsabs>() {
            @Override
            public int compare(Newsabs newsabs, Newsabs t1) {
                if (newsabs.time > t1.time) return -1;
                if (newsabs.time  == t1.time) return 0;
                return 1;
            }
        });
        return alis;
    }

    public static   ArrayList<Newsabs> searchAbstractOffline(String keyword) {
        List<Newsabs> lis = Newsabs.find(Newsabs.class, "jsonstr like ?", "%" + keyword + "%");
        ArrayList<Newsabs> alis = new ArrayList<Newsabs>();
        for (Newsabs i: lis) {
            boolean flag = true;
            for (String t: User.getStopList())
                if (i.jsonstr.contains(t)) flag = false;
            if (flag) alis.add(i);
        }
        Collections.sort(alis, new Comparator<Newsabs>() {
            @Override
            public int compare(Newsabs newsabs, Newsabs t1) {
                if (newsabs.time > t1.time) return -1;
                if (newsabs.time  == t1.time) return 0;
                return 1;
            }
        });
        return alis;
    }

    public static   ArrayList<Newsabs> getCachedAbstractByAdvice() {
        HashMap<Long, Double> cnt = new HashMap<Long, Double>();
        List<PreferPhrase> pp = PreferPhrase.find(PreferPhrase.class, "user = ?", User.getUser().getId().toString());
        for (PreferPhrase p: pp)
        {
            List<Newsabs> lis = Newsabs.find(Newsabs.class, "jsonstr like ?", "%" + p.phrase.word + "%");
            for (Newsabs i: lis) {
                boolean flag = true;
                for (String t: User.getStopList())
                    if (i.jsonstr.contains(t)) flag = false;
                if (!flag) continue;
                if (ViewedNews.find(ViewedNews.class, "owner = ? and data = ?", User.getUser().getId().toString(), i.getId().toString()).size() > 0) continue;
                if (cnt.containsKey(i.getId())) cnt.put(i.getId(), cnt.get(i.getId()) + p.d);
                else cnt.put(i.getId(), p.d);
            }
        }
        final HashMap<Long, Double> fcnt = cnt;
        ArrayList<Long> res_id = new ArrayList<Long>();
        res_id.addAll(cnt.keySet());
        Collections.sort(res_id, new Comparator<Long>() {
            @Override
            public int compare(Long aLong, Long t1) {
                return fcnt.get(aLong) < fcnt.get(t1) ? 1 : -1;
            }
        });
        ArrayList<Newsabs> res = new ArrayList<Newsabs>();
        for (int i=0; i < res_id.size(); i++) res.add(Newsabs.findById(Newsabs.class, res_id.get(i)));
        return res;
    }

    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getNewsID() {return newsid;}
    public String getFirstPicture() {
        return pictures.length() > 0 ? pictures.split(";")[0] : null;
    }
}
