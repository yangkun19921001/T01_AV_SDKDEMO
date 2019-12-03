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
    }
}
