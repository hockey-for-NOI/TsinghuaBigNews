package com.example.sth.net;

import android.os.Bundle;

import com.example.john.bignews.MainApplication;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.SpeechError;

/**
 * Created by Shith on 2017/9/12.
 */

public class Speech {
    public static void start(String str){
        //合成监听器
        SynthesizerListener mSynListener = new SynthesizerListener() {
            //会话结束回调接口，没有错误时，error为null
            public void onCompleted(SpeechError error) {
            }

            //缓冲进度回调
            //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            }

            //开始播放
            public void onSpeakBegin() {
            }

            //暂停播放
            public void onSpeakPaused() {
            }

            //播放进度回调
            //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
            }

            //恢复播放回调接口
            public void onSpeakResumed() {
            }

            //会话事件回调接口
            public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            }
        };

        SpeechSynthesizer mTts= SpeechSynthesizer.getSynthesizer();
        //3.开始合成
        mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);
    }


    public static void stop(){
        SpeechSynthesizer mTts= SpeechSynthesizer.getSynthesizer();
        mTts.stopSpeaking();
    }

}
