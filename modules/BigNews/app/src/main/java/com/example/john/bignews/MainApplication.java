package com.example.john.bignews;

import com.example.hq.usermanager.StoredUser;
import com.iflytek.cloud.SpeechSynthesizer;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by ThinkPad on 2017/9/9.
 */

public class MainApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        StoredUser.setup();

        //语音模块
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59b64a3e");
        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(this, null);
        //2.合成参数设置，详见《iFlytek MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100

    }

    @Override
    public void onTerminate() {
        StoredUser.finish();
        SugarContext.terminate();
        super.onTerminate();
    }
}
