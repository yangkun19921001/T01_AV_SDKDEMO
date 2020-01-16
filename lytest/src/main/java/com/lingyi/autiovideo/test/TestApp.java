package com.lingyi.autiovideo.test;

import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.bnc.activity.PttApplication;
import com.bnc.activity.T01Helper;
import com.bnc.activity.service.module.log.CrashHandler;

/**
 * Created by yangk on 2018/1/4.
 */

public class TestApp extends PttApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());
        /**
         * 初始化 SDK
         */
        initT01SDK();

        Utils.init(this);

        CrashHandler.getInstance().register(this);

    }

    private void initT01SDK() {
        //初始化 SDK
        T01Helper.getInstance().initAppContext(getApplicationContext());
        //手机不需要多路
        T01Helper.getInstance().getCallEngine().setMultipleLines(true);

        //交给Java硬编码
        T01Helper.getInstance().getSetEngine().setJavaMediacodec(true);
        //0:320P 1:480P 2:720P 3:1080P
        T01Helper.getInstance().getSetEngine().setVideoCallInCallQuality(2);

        if (T01Helper.getInstance().getCallEngine().isMultipleLines()) {
            //设置多路全部禁言模式（true:不推音频流->对端听不见发送端的声音，false :推送音频流）
            T01Helper.getInstance().getCallEngine().setMoreAudioMute(true);
            //设置多路全部播放模式（true:播放->播放对端的声音，false :不播放对端声音）
            T01Helper.getInstance().getCallEngine().setMoreAudioPlay(false);
        }
    }
}
