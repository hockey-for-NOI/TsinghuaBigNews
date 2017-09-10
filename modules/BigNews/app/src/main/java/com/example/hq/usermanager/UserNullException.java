package com.example.hq.usermanager;

import java.lang.Exception;

/**
 * Created by ThinkPad on 2017/9/10.
 */

public class UserNullException extends Exception {
    public UserNullException() {super("Haven't login yet.");}
}
