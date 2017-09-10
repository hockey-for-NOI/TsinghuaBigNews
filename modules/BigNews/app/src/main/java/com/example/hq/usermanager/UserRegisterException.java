package com.example.hq.usermanager;

/**
 * Created by ThinkPad on 2017/9/5.
 */

public class UserRegisterException extends Exception {
    public UserRegisterException()
    {
        super("Duplicate Username.");
    }

    public UserRegisterException(String dummy)
    {
        super("Please fill in username.");
    }

    public UserRegisterException(int dummy) {super("Password too weak");}
    public UserRegisterException(double dummy) {super("Confirm password mismatch.");}
}
