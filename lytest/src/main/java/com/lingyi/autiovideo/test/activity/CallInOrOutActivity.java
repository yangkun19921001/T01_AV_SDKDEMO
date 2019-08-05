package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.CALL_TYPE;
import com.lingyi.autiovideo.test.Constants;
import com.lingyi.autiovideo.test.R;

import org.doubango.ngn.sip.NgnAVSession;

import static org.doubango.ngn.Constants.callType;

public class CallInOrOutActivity extends Activity {

    private NgnAVSession mFirstSession;
    private CallReceiver mCallReceiver;
    private View callInOut;
    private View audio_call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_in);

        mFirstSession = getFirstSession();

        if (getCallType()) {
            findViewById(R.id.acceptCall).setVisibility(View.GONE);
        }


        mCallReceiver = new CallReceiver();

        addClickListener();
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCallReceiver);
    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.CALL_ACION);
        intentFilter.addAction(Constants.CALL_IN_OR_OUR_ACION);
        registerReceiver(mCallReceiver, intentFilter);
    }

    private void addClickListener() {
        findViewById(R.id.acceptCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirstSession != null)
                    T01Helper.getInstance().getCallEngine().acceptCall(mFirstSession);
            }
        });

        findViewById(R.id.hangUpCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirstSession != null)
                    T01Helper.getInstance().getCallEngine().hangUpCall(mFirstSession);
            }
        });
    }

    /**
     * 获取第一个线路
     *
     * @return
     */
    private NgnAVSession getFirstSession() {
        if (getIntent() != null && getIntent().getLongExtra(Constants.SESSION_ID, -1) != -1) {
            //根据 sessionID 获取线路
            return T01Helper.getInstance().getCallEngine().getNgnAVSession(getIntent().getLongExtra(Constants.SESSION_ID, -1));
        }
        return null;
    }

    public boolean getCallType() {
        if (getIntent() != null && getIntent().getIntExtra(Constants.CALL_TYPE, -1) != -1) {
            //根据 sessionID 获取线路
            int callType = getIntent().getIntExtra(Constants.CALL_TYPE, -1);
            if (callType == CALL_TYPE.AUDIO_CALL_OUT.ordinal() || callType == CALL_TYPE.VIDEO_CALL_OUT.ordinal()) {
                return true;
            }
        }

        return false;
    }


    /**
     * 接收监听器消息
     */
    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getAction().equals(Constants.CALL_ACION)) {
                long callType = intent.getIntExtra(Constants.CALL_TYPE, -1);
                long sessionId = intent.getLongExtra(Constants.SESSION_ID, -1);
                if (callType != -1) {
                    if (callType == CALL_TYPE.VIDEO_TERMINATED.ordinal()) {
                        if (sessionId != -1) {
                            //拿到当前会话 session 可以理解为线路
                            NgnAVSession ngnAVSession = T01Helper.getInstance().getCallEngine().getNgnAVSession(sessionId);
                            if (ngnAVSession != null)
                                //认为还有其它线路正在通话，不能直接关闭页面
                                ngnAVSession.hangUpCall();
                            finish();
                        }
                    } else if (callType == CALL_TYPE.AUDIO_TERMINATED.ordinal()) {
                        if (sessionId != -1) {
                            //拿到当前会话 session 可以理解为线路
                            NgnAVSession ngnAVSession = T01Helper.getInstance().getCallEngine().getNgnAVSession(sessionId);
                            //认为还有其它线路正在通话，不能直接关闭页面
                            if (ngnAVSession != null)
                                ngnAVSession.hangUpCall();
                            finish();
                        }
                    } else if (callType == CALL_TYPE.ERROR.ordinal()) {
                        if (sessionId == -1) return;
                        //拿到当前会话 session 可以理解为线路
                        NgnAVSession ngnAVSession = T01Helper.getInstance().getCallEngine().getNgnAVSession(sessionId);
                        //认为还有其它线路正在通话，不能直接关闭页面
                        if (ngnAVSession != null)
                            ngnAVSession.hangUpCall();
                        finish();
                    }

                }
            } else if (intent != null && intent.getAction() != null && intent.getAction().equals(Constants.CALL_IN_OR_OUR_ACION)) {
                long callType = intent.getIntExtra(Constants.CALL_TYPE, -1);
                if (callType != -1) {
                    //拿如果已经收到通话中的消息就关闭掉当前页面
                    if (callType == CALL_TYPE.VIDEO_CALL_IN_CALL.ordinal() || callType == CALL_TYPE.AUDIO_CALL_IN_CALL.ordinal()) {
                        finish();
                    }
                }

            }
        }
    }

}