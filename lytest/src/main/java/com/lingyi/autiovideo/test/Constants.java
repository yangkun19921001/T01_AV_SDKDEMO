package com.lingyi.autiovideo.test;

import android.widget.FrameLayout;

import org.doubango.ngn.sip.NgnAVSession;

import java.util.HashMap;

public class Constants {
    /**
     * 当前通话的 Session ID
     */
    public static final String SESSION_ID = "SESSION_ID";
    /**
     * 当前通话的号码
     */
    public static final String CALL_NUMBER = "CALL_NUMBER";
    public static final String CALL_ACION = "com.lingyi.autiovideo.test.CALL_ACION";
    public static final String CALL_TYPE = "CALL_TYPE";
    public static final String CALL_ERROR = "CALL_ERROR";
    public static final String CALL_IN_OR_OUR_ACION = "com.lingyi.autiovideo.test.CALL_IN_OR_OUR_ACION";


    /**
     * 当前播放的 Session ID 队列
     */
    public static HashMap<Long, NgnAVSession> mSessionMap = new HashMap<>();
    public static HashMap<Long, FrameLayout> mSessionLayoutMap = new HashMap<>();
    public static boolean isShow = false;
}
