package com.example.hq.usermanager;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class UserChangePasswordException extends Exception {
    public UserChangePasswordException()
    {
        super("Old password mismatch.");
    }

    public UserChangePasswordException(int dummy)
    {
        super("Confirm password mismatch.");
    }

    public UserChangePasswordException(String dummy)
    {
        super("Password too weak.");
    }
}
