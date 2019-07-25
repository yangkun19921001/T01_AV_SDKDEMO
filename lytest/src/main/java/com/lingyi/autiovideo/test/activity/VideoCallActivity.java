package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.CALL_TYPE;
import com.lingyi.autiovideo.test.Constants;
import com.lingyi.autiovideo.test.R;

import org.doubango.ngn.sip.NgnAVSession;

import java.util.HashMap;
import java.util.Set;

public class VideoCallActivity extends Activity {

    /**
     * 当前播放的 Session ID 队列
     */
    private HashMap<Long, NgnAVSession> mSessionMap = new HashMap<>();
    private HashMap<Long, FrameLayout> mSessionLayoutMap = new HashMap<>();
    private CallReceiver mCallReceiver;
    private FrameLayout mRemote_1, mRemote_2, mRemote_3, mRemote_4, mLocal;
    private NgnAVSession mFirstSession;
    private EditText et_number;
    private Button btnMakeCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_call_in_call);

        mRemote_1 = findViewById(R.id.video_call_remote_video_1);
        mRemote_2 = findViewById(R.id.video_call_remote_video_2);
        mRemote_3 = findViewById(R.id.video_call_remote_video_3);
        mRemote_4 = findViewById(R.id.video_call_remote_video_4);
        et_number = findViewById(R.id.et_number);
        btnMakeCall = findViewById(R.id.btn_make_call);
        mLocal = findViewById(R.id.video_call_local_video);




        //不发送视频流
        T01Helper.getInstance().getCallEngine().startPreviewLocalVideo(mLocal, false);

        //获取第一个线路
        mFirstSession = getFirstSession();
        mSessionMap.put(mFirstSession.getId(), mFirstSession);

        previewRemote(mFirstSession, mRemote_1);
        registerReceiver();

        btnMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_number.getText().toString().trim())) {
                    T01Helper.getInstance().getCallEngine().call(et_number.getText().toString().trim(),
                            org.doubango.ngn.Constants.IVoipLaunchType.VOIP_LAUNCH_TYPE_VIDEO,
                            et_number.getText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "请输入号码", Toast.LENGTH_SHORT).show();
                }
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

    /**
     * 预览对方框口
     *
     * @param ngnAVSession
     * @param mRemote_1
     */
    private void previewRemote(NgnAVSession ngnAVSession, FrameLayout mRemote) {

        if (ngnAVSession != null) {
            T01Helper.getInstance().getCallEngine().startPreviewRemoteVideo(ngnAVSession, mRemote);
            if (!mSessionLayoutMap.containsKey(ngnAVSession.getId()))
                mSessionLayoutMap.put(ngnAVSession.getId(), mRemote);
        }
    }

    /**
     * 注册接收电话监听器传过来的消息
     */
    public void registerReceiver() {
        mCallReceiver = new CallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.CALL_ACION);
        registerReceiver(mCallReceiver, intentFilter);
    }


    @Override
    public void onStart() {
        super.onStart();
        T01Helper.getInstance().getCallEngine().onCallStart();
    }

    public void onResume() {
        super.onResume();
        T01Helper.getInstance().getCallEngine().onResume();

    }

    private void showRemote() {
        //        for (Long aLong : mSessionMap.keySet()) {
//            NgnAVSession ngnAVSession = mSessionMap.get(aLong);
//            T01Helper.getInstance().getCallEngine().startPreviewRemoteVideo(ngnAVSession, mSessionLayoutMap.get(aLong));
//        }
    }

    public void onPause() {
        super.onPause();
        T01Helper.getInstance().getCallEngine().onCallPause();
    }

    public void onStop() {
        super.onStop();
        T01Helper.getInstance().getCallEngine().onCallStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestory();

    }

    private void onDestory() {
        T01Helper.getInstance().getCallEngine().onCallDestroy();
        T01Helper.getInstance().getCallEngine().stopLocalVideo();
        T01Helper.getInstance().getCallEngine().stopPreviewVideo();
        if (mCallReceiver != null) {
            unregisterReceiver(mCallReceiver);
            mCallReceiver = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hangUpAllSession();
    }

    /**
     * 接收监听器消息
     */
    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getAction().equals(Constants.CALL_ACION)) {
                long callType = intent.getIntExtra(Constants.CALL_TYPE, -1);
                if (callType != -1) {
                    //拿到多路通话的线路
                    if (callType == CALL_TYPE.VIDEO_CALL_IN_CALL.ordinal()) {
                        long sessionId = intent.getLongExtra(Constants.SESSION_ID, -1);
                        if (sessionId != -1) {
                            //拿到当前会话 session 可以理解为线路
                            NgnAVSession ngnAVSession = T01Helper.getInstance().getCallEngine().getNgnAVSession(sessionId);
                            if (ngnAVSession != null)
                                mSessionMap.put(sessionId, ngnAVSession);
                            if (mSessionMap.size() == 2) {
                                mRemote_2.removeAllViews();
                                previewRemote(ngnAVSession, mRemote_2);
                            } else if (mSessionMap.size() == 3) {
                                mRemote_3.removeAllViews();
                                previewRemote(ngnAVSession, mRemote_3);
                            } else if (mSessionMap.size() == 4) {
                                mRemote_4.removeAllViews();
                                previewRemote(ngnAVSession, mRemote_4);
                            }
                        }
                        //挂断线路的通知
                    } else if (callType == CALL_TYPE.VIDEO_TERMINATED.ordinal()) {
                        long sessionId = intent.getLongExtra(Constants.SESSION_ID, -1);
                        if (sessionId != -1) {
                            if (mSessionMap.containsKey(sessionId)){
                                mSessionMap.get(sessionId).hangUpCall();
                                mSessionMap.remove(sessionId);
                            }
                            mSessionLayoutMap.remove(sessionId).removeAllViews();
                            if (mSessionLayoutMap.size() == 0) {
                                finish();
                            }
                        }
                    }
                }

            }

        }

    }

    /**
     * 挂断所有的线路
     */
    private void hangUpAllSession() {
        if (mSessionMap.size() > 0) {
            for (Long aLong : mSessionMap.keySet()) {
                mSessionMap.get(aLong).hangUpCall();
            }
            finish();
        }
    }
}
