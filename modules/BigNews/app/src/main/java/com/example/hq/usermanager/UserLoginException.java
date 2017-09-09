package com.example.hq.usermanager;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class UserLoginException extends Exception {
    public UserLoginException()
    {
        super("No such Username.");
    }

    public UserLoginException(int dummy)
    {
        super("Password mismatch.");
    }
}
