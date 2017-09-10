package com.example.john.bignews;

import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by ThinkPad on 2017/9/9.
 */

public class MainApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
