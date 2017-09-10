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

    public StoredUser() {}

    public static   void    setup()
    {
        stu = StoredUser.findAll(StoredUser.class).next();
    }

    public static   void    finish()
    {
        StoredUser.deleteAll(StoredUser.class);
        stu.save();
    }

    public static   void    rememberUsername() {stu.stored = User.getUser();}
    public static   void    rememberPassword() {stu.stored = User.getUser(); stu.save_passwd = true;}
    public static   void    forgetUsername() {stu.stored = null; stu.save_passwd = false;}
    public static   void    forgetPassword() {stu.save_passwd = false;}
}
