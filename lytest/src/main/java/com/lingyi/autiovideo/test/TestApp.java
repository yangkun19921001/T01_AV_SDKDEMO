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
        initT01SDK();

        Utils.init(this);

    }

    private void initT01SDK() {
        T01Helper.getInstance().initAppContext(getApplicationContext());
        //手机不需要多路
        T01Helper.getInstance().getCallEngine().setMultipleLines(false);
        //交给Java硬编码
        T01Helper.getInstance().getSetEngine().isJavaMediacodec(true);
    }
}
