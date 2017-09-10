package com.example.john.bignews;

import com.example.hq.usermanager.StoredUser;
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
        StoredUser.setup();
    }

    @Override
    public void onTerminate() {
        StoredUser.finish();
        SugarContext.terminate();
        super.onTerminate();
    }
}
