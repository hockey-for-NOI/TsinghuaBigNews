package com.example.hq.usermanager;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by ThinkPad on 2017/9/13.
 */

public class PreferPhrase extends SugarRecord {
    User user;
    Phrase phrase;
    double d;

    public PreferPhrase() {}

    public PreferPhrase(User u, Phrase p, double d) {this.user = u; this.phrase = p; this.d = d;}

    public PreferPhrase add(double d) {this.d += d; return this;}

    public static void add(User u, Phrase p, double d){
        List<PreferPhrase> tmp = PreferPhrase.find(PreferPhrase.class, "user = ? and phrase = ?", u.getId().toString(), p.getId().toString());
        if (tmp.size() > 0) tmp.get(0).add(d).save();
        else new PreferPhrase(u, p, d).save();
    }

}
