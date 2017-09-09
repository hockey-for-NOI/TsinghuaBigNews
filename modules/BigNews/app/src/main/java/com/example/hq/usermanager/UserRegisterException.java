package com.example.hq.usermanager;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class UserRegisterException extends Exception {
    public UserRegisterException()
    {
        super("Duplicate Username.");
    }

    public UserRegisterException(int dummy)
    {
        super("Password too weak.");
    }
}
