package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.CALL_TYPE;
import com.bnc.activity.service.db.DataDao;
import com.lingyi.autiovideo.test.Constants;
import com.lingyi.autiovideo.test.R;

import org.doubango.ngn.media.NgnProxyVideoConsumerGL;
import org.doubango.ngn.sip.NgnAVSession;

import java.util.HashMap;

public class VideoCallActivity extends Activity {

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


        //预览本地并发送视频流，false：为内部发送视频流，true 为自己处理视频采集
        T01Helper.getInstance().getCallEngine().startPreviewLocalVideo(mLocal, false);


        //获取第一个线路
        mFirstSession = getFirstSession();
        if (mFirstSession != null)
            Constants.mSessionMap.put(mFirstSession.getId(), mFirstSession);

        if (mFirstSession != null)
            previewRemote(mFirstSession, mRemote_1);
        registerReceiver();

        btnMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_number.getText().toString().trim())) {
                    Log.d("IntercomFragment", "callNunber:" + et_number.getText().toString().trim() + " callType:Constants.IVoipLaunchType.VOIP_LAUNCH_TYPE_VIDEO" + " callName:" + "测试多路");
                    T01Helper.getInstance().getCallEngine().call(et_number.getText().toString().trim(),
                            org.doubango.ngn.Constants.IVoipLaunchType.VOIP_LAUNCH_TYPE_VIDEO,
                            "测试多路");
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
     * @param mRemote_1
     * @param ngnAVSession
     */
    private NgnProxyVideoConsumerGL.NgnProxyVideoConsumerGLPreview previewRemote(NgnAVSession ngnAVSession, FrameLayout mRemote) {
        if (ngnAVSession != null) {
            NgnProxyVideoConsumerGL.NgnProxyVideoConsumerGLPreview ngnProxyVideoConsumerGLPreview = T01Helper.getInstance().getCallEngine().startPreviewRemoteVideo(ngnAVSession, mRemote);
            if (!Constants.mSessionLayoutMap.containsKey(ngnAVSession.getId()))
                Constants.mSessionLayoutMap.put(ngnAVSession.getId(), mRemote);

            return ngnProxyVideoConsumerGLPreview;
        }
        return null;
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
//
//        if (Constants.isShow) {
//            showRemote();
//        }

        if (T01Helper.getInstance().getSetEngine().isCameraFront()) {
            T01Helper.getInstance().getCallEngine().changeCamera(isChangeCamera = false);
        }


    }

    boolean isShow = false;

    private void showRemote() {
        if (Constants.mSessionMap.size() > 0)
            for (Long aLong : Constants.mSessionMap.keySet()) {
                NgnAVSession ngnAVSession = Constants.mSessionMap.get(aLong);
                T01Helper.getInstance().getCallEngine().startPreviewRemoteVideo(ngnAVSession, mRemote_1);
            }
    }

    public void onPause() {
        super.onPause();
        T01Helper.getInstance().getCallEngine().onCallPause();
    }

    public void onStop() {
        super.onStop();
        T01Helper.getInstance().getCallEngine().onCallStop();
    }


    private void onDestorys() {
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
        Constants.isShow = true;
        hangUpAllSession();
//        startActivity(new Intent(this,MainActivity.class));
    }


    private boolean isChangeCamera = false;

    public void changeCamera(View view) {
        T01Helper.getInstance().getCallEngine().changeCamera(isChangeCamera = !isChangeCamera);
    }

    /**
     * 接收监听器消息
     */
    private class CallReceiver extends BroadcastReceiver {
        NgnProxyVideoConsumerGL.NgnProxyVideoConsumerGLPreview ngnProxyVideoConsumerGLPreview = null;

        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getAction().equals(Constants.CALL_ACION)) {
                long callType = intent.getIntExtra(Constants.CALL_TYPE, -1);
                if (callType != -1) {
                    //拿到多路通话的线路
                    if (callType == CALL_TYPE.VIDEO_CALL_IN_CALL.ordinal()) {
                        long sessionId = intent.getLongExtra(Constants.SESSION_ID, -1);
                        if (sessionId != -1) {
                            //拿到当前会话 session 可以理解为线路
                            final NgnAVSession ngnAVSession = T01Helper.getInstance().getCallEngine().getNgnAVSession(sessionId);
                            if (ngnAVSession != null)
                                Constants.mSessionMap.put(sessionId, ngnAVSession);
                            if (Constants.mSessionMap.size() == 2) {
                                previewRemote(ngnAVSession, mRemote_2);
                            } else if (Constants.mSessionMap.size() == 3) {
                                mRemote_3.removeAllViews();
                                previewRemote(ngnAVSession, mRemote_3);
                            } else if (Constants.mSessionMap.size() == 4) {
                                mRemote_4.removeAllViews();
                                previewRemote(ngnAVSession, mRemote_4);
                            }
                        }
                        //挂断线路的通知
                    } else if (callType == CALL_TYPE.VIDEO_TERMINATED.ordinal()) {
                        long sessionId = intent.getLongExtra(Constants.SESSION_ID, -1);
                        if (sessionId != -1) {
                            if (Constants.mSessionMap.containsKey(sessionId)) {
                                Constants.mSessionMap.get(sessionId).hangUpCall();
                                Constants.mSessionMap.remove(sessionId);
                            }
                            if (Constants.mSessionLayoutMap.remove(sessionId) != null)
                                Constants.mSessionLayoutMap.remove(sessionId);
                            if (Constants.mSessionLayoutMap.size() == 0) {
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
        if (Constants.mSessionMap.size() > 0) {
            for (Long aLong : Constants.mSessionMap.keySet()) {
                Constants.mSessionMap.get(aLong).hangUpCall();
            }
            finish();
        }
    }
}
