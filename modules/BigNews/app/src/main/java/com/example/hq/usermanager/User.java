package com.example.hq.usermanager;

import java.lang.reflect.Array;
import java.util.*;

import com.example.sth.net.Category;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class User extends SugarRecord {
    String  name;
    String  salted_passwd;
    String  personal_settings;

    private static  User    user;
    public static   User    getUser() {return user;}

    public String   getName() {return name;}

    public User() {
    }

    private User(String name, String raw_passwd)
    {
        this.name = name;
        this.salted_passwd = salted(raw_passwd);
        this.personal_settings = getDefaultPersonalSettings();
    }

    private static  String  getDefaultPersonalSettings()
    {
        JSONObject obj = new JSONObject();

        JSONArray favouriteCategories = new JSONArray(Category.getAllNames());

        try {
            obj.put("favouriteCategories", favouriteCategories);
        }
        catch (JSONException e) {}

        return obj.toString();
    }

    private ArrayList<String> getFC()
    {
        ArrayList<String> res = null;
        try
        {
            JSONArray jres = (new JSONObject(this.personal_settings)).getJSONArray("favouriteCategories");
            res = new ArrayList<String>();
            for (int i=0; i<jres.length(); i++) res.add(jres.getString(i));
        }
        catch (JSONException e) {res = Category.getAllNames();} finally {return res;}
    }

    public  static  ArrayList<String> getFavouriteCategories() {
        if (user == null) return Category.getAllNames();
        return user.getFC();
    }

    private void    setFC(List<String> fc) {
        try {
            JSONObject obj = new JSONObject(this.personal_settings);
            JSONArray jarr = new JSONArray(fc);
            obj.put("favouriteCategories", jarr);
            this.personal_settings = obj.toString();
        } catch (JSONException e) {}
    }

    public  static  void    setFavouriteCategories(List<String> fc) throws UserNullException {
        if (user == null) throw new UserNullException();
        user.setFC(fc);
    }

    private static  String  salted(String str1)
    {
        StringBuffer buf = new StringBuffer();
        String  salt = "salt";
        for (int i=0; i<str1.length(); i++)
            buf.append(str1.charAt(i) ^ salt.charAt(i & 3));
        return buf.toString();
    }

    private static  boolean passwdChk(String str1)
    {
        if (str1.length() < 4) return false;
        return true;
    }
    public  static  User    register(String username, String raw_passwd) throws UserRegisterException
    {
        List<User> lu = User.find(User.class, "name = ?", username);
        if (!lu.isEmpty()) throw new UserRegisterException();

        if (!passwdChk(raw_passwd)) throw new UserRegisterException(0);

        User u = new User(username, salted(raw_passwd));
        u.save();
        return u;
    }

    public  static  User    login(String username, String raw_passwd) throws UserLoginException
    {
        List<User> lu = User.find(User.class, "name = ?", username);
        if (lu.isEmpty()) throw new UserLoginException();
        User    u = lu.get(0);
        if (u.salted_passwd.compareTo(salted(raw_passwd)) == 0) return u;
        throw new UserLoginException(0);
    }

    public void  changePassword(String raw_old_passwd, String raw_new_passwd, String raw_confirm_passwd) throws UserChangePasswordException{
        if (this.salted_passwd.compareTo(salted(raw_old_passwd)) != 0)
            throw new UserChangePasswordException();
        if (raw_new_passwd.compareTo(salted(raw_confirm_passwd)) != 0)
            throw new UserChangePasswordException(0);
        if (!passwdChk(raw_new_passwd))
            throw new UserChangePasswordException("");
        this.salted_passwd = salted(raw_new_passwd);
    }
}
