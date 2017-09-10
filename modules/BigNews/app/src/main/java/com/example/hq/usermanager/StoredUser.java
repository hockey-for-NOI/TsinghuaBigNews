package com.example.hq.usermanager;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by ThinkPad on 2017/9/10.
 */

public class StoredUser extends SugarRecord {
    User    stored;
    boolean save_passwd;

    private static  StoredUser stu;
    private static  User    guest;

    public static   User    getGuest() {return guest;}

    public StoredUser() {}

    public static   void    setup()
    {
        try {
            guest = User.register("guest", "__builtin__guest__passwd__", "__builtin__guest__passwd__");
        } catch (UserRegisterException e) {}
        guest = User.find(User.class, "name = ?", "guest").get(0);
        if (StoredUser.findAll(StoredUser.class).hasNext()) {
            stu = StoredUser.findAll(StoredUser.class).next();
            if (stu.save_passwd) User.setUser(stu.stored);
            else User.setUser(guest);
        }
        else {
            stu = new StoredUser();
            stu.stored = guest;
            stu.save_passwd = true;
            stu.save();
            User.setUser(guest);
        }
    }

    public static   void    finish()
    {
        StoredUser.deleteAll(StoredUser.class);
        stu.save();
    }

    public static   void    rememberUsername() {stu.stored = User.getUser();}
    public static   void    rememberPassword() {stu.stored = User.getUser(); stu.save_passwd = true;}
    public static   void    forgetUsername() {stu.stored = guest; stu.save_passwd = true;}
    public static   void    forgetPassword() {stu.save_passwd = false;}

    public static   String  cachedUsername() {return stu.stored.getName();}
    public static   boolean cachedPassword() {return stu.save_passwd;}
}
