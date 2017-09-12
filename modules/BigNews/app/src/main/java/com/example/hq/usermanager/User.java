package com.example.hq.usermanager;

import java.lang.reflect.Array;
import java.lang.reflect.UndeclaredThrowableException;
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
    public static   void   setUser(User u) {user = u;}
    public static   boolean isGuest() {return user.getName().compareTo("guest") == 0;}

    public String   getName() {return name;}

    public User() {
    }

    private User(String name, String raw_passwd)
    {
        this.name = name;
        this.salted_passwd = salted(raw_passwd);
        this.personal_settings = (new PersonalSettings()).toString();
    }

    public PersonalSettings getPersonalSettings() {return new PersonalSettings(personal_settings);}

    public  static  ArrayList<String> getFavouriteCategories() {
        if (isGuest()) return Category.getAllNames();
        return user.getPersonalSettings().getFavouriteCategories();
    }

    public  static  void    setFavouriteCategories(ArrayList<String> fc) throws UserNullException {
        if (isGuest()) throw new UserNullException();
        user.personal_settings = user.getPersonalSettings().setFavouriteCategories(fc).toString();
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
    public  static  User    register(String username, String raw_passwd,
                                     String confirm_passwd) throws UserRegisterException
    {
        List<User> lu = User.find(User.class, "name = ?", username);
        if (!lu.isEmpty()) throw new UserRegisterException();
        if (username.length() == 0) throw new UserRegisterException("");
        if (!passwdChk(raw_passwd)) throw new UserRegisterException(0);
        if (raw_passwd.compareTo(confirm_passwd) != 0) throw new UserRegisterException(0.1);

        User u = new User(username, raw_passwd);
        u.save();
        return user = u;
    }

    public  static  User    login(String username, String raw_passwd,
                                  boolean rememberName, boolean rememberPasswd) throws UserLoginException
    {
        List<User> lu = User.find(User.class, "name = ?", username);
        if (lu.isEmpty()) throw new UserLoginException();
        User u = lu.get(0);
        if (u.salted_passwd.compareTo(salted(raw_passwd)) != 0) throw new UserLoginException(0);
        user = u;
        if (rememberName)
        {
            if (rememberPasswd) StoredUser.rememberPassword();
            else {StoredUser.rememberUsername(); StoredUser.forgetPassword();};
        }
        else StoredUser.forgetUsername();
        return u;
    }

    public static void  changePassword(String raw_old_passwd, String raw_new_passwd, String raw_confirm_passwd)
            throws UserChangePasswordException, UserNullException {
        if (isGuest()) throw new UserNullException();
        user.changePasswd(raw_old_passwd, raw_new_passwd, raw_confirm_passwd);
    }

    public void  changePasswd(String raw_old_passwd, String raw_new_passwd, String raw_confirm_passwd) throws UserChangePasswordException{
        if (this.salted_passwd.compareTo(salted(raw_old_passwd)) != 0)
            throw new UserChangePasswordException();
        if (raw_new_passwd.compareTo(salted(raw_confirm_passwd)) != 0)
            throw new UserChangePasswordException(0);
        if (!passwdChk(raw_new_passwd))
            throw new UserChangePasswordException("");
        this.salted_passwd = salted(raw_new_passwd);
    }

    public static   void    logout() throws UserNullException {
        if (user.isGuest()) throw new UserNullException();
        user = StoredUser.getGuest();
        StoredUser.forgetUsername();
    }

    public static   void    logout_anyway() {user = null;}

}
