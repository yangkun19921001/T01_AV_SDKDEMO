package com.lingyi.autiovideo.test;

import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.bnc.activity.PttApplication;
import com.bnc.activity.T01Helper;

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
        T01Helper.getInstance().initAppContext(getApplicationContext());
        Utils.init(this);



    }
}
