package com.lingyi.autiovideo.test;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.bnc.activity.PttApplication;
import com.bnc.activity.T01Helper;
import com.bnc.activity.service.module.log.CrashHandler;
import com.tencent.bugly.crashreport.CrashReport;

import org.doubango.ngn.media.mixer.MixerManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by yangk on 2018/1/4.
 */

public class TestApp extends PttApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());

        initCarsh();
        /**
         * 初始化 SDK
         */
        initT01SDK();

        Utils.init(this);


    }

    private void initT01SDK() {
        //初始化 SDK
        T01Helper.getInstance().initT01SDK(getApplicationContext());
        //需要多路 true
        T01Helper.getInstance().getCallEngine().setMultipleLines(true);
        //交给Java硬编码
        T01Helper.getInstance().getSetEngine().setJavaMediacodec(true);
        //0:320P 1:480P 2:720P 3:1080P
        T01Helper.getInstance().getSetEngine().setVideoCallInCallQuality(2);
        if (T01Helper.getInstance().getCallEngine().isMultipleLines()) {
            //设置多路全部禁言模式（true:不推音频流->对端听不见发送端的声音，false :推送音频流）
            T01Helper.getInstance().getCallEngine().setMoreAudioMute(false);
            //设置多路全部播放模式（true:播放->播放对端的声音，false :不播放对端声音）
            T01Helper.getInstance().getCallEngine().setMoreAudioPlay(true);
            //多路呼叫需要混音
//            T01Helper.getInstance().getSetEngine().setEnableMixer(true);
        }
    }

    public void initCarsh(){
        Context context = getApplicationContext();
            // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "8f2decddec", BuildConfig.DEBUG, strategy);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
