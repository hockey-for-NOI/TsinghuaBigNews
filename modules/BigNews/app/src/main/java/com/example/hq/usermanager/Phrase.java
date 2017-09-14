package com.example.hq.usermanager;

import com.example.sth.net.Baike;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2017/9/13.
 */

public class Phrase extends SugarRecord {
    String word;
    String baikeurl;

    public  Phrase() {}

    public Phrase(String w){
        word = w;
        new Thread() {
            @Override
            public void run() {
                while (baikeurl != null) {
                    baikeurl = Baike.validateBaike(word);
                    try {
                        sleep(1000);
                    } catch (Exception e) {}
                }
                save();
            }
        }.start();
    }

    public  static  void    pourPhrase(Newsdata data)
    {
        try{
            JSONArray jarr = new JSONObject(data.jsonstr).getJSONArray("Keywords");
            for (int i=0; i<jarr.length(); i++)
            {
                JSONObject jobj = jarr.getJSONObject(i);
                Phrase p = new Phrase(jobj.getString("word"));
                p.save();
                PreferPhrase.add(User.getUser(), p, jobj.getDouble("score"));
            }
        }
        catch (JSONException e) {}
    }
}
