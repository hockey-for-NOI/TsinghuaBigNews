package com.example.hq.usermanager;

import com.example.sth.net.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ThinkPad on 2017/9/10.
 */

public class PersonalSettings {
    public ArrayList<String> favouriteCategories;
    public ArrayList<String> stopList;

    private void setDefault()
    {
        favouriteCategories = Category.getAllNames();
        stopList = new ArrayList<String>();
    }

    public PersonalSettings() {
        setDefault();
    }

    public PersonalSettings(String s) {
        try {
            JSONObject jobj = new JSONObject(s);

            JSONArray jarr = jobj.getJSONArray("favouriteCategories");
            favouriteCategories = new ArrayList<String>();
            favouriteCategories.clear();
            for (int i=0; i<jarr.length(); i++) favouriteCategories.add(jarr.getString(i));

            jarr = jobj.getJSONArray("stopList");
            stopList = new ArrayList<String>();
            stopList.clear();
            for (int i=0; i<jarr.length(); i++) stopList.add(jarr.getString(i));
        }
        catch (JSONException e) {
            setDefault();
        }
    }

    @Override
    public String toString() {
        JSONObject jobj = new JSONObject();

        try {
            JSONArray jfc = new JSONArray(favouriteCategories);
            jobj.put("favouriteCategories", jfc);
            JSONArray jsl = new JSONArray(stopList);
            jobj.put("stopList", jsl);
        }
        catch (JSONException e) {}

        return jobj.toString();
    }

    public PersonalSettings setFavouriteCategories(ArrayList<String> c) {favouriteCategories = c; return this;}
    public PersonalSettings setStopList(ArrayList<String> s) {stopList = s; return this;}
}
