package com.lingyi.autiovideo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bnc.activity.PttApplication;
import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.CallEngine;
import com.bnc.activity.receiver.SipEventReceiver;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.fragment.IntercomFragment;
import com.lingyi.autiovideo.test.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mTextMessage;

    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mReplace = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    mReplace = 0;
                    FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    mReplace = 1;
//                    FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    mReplace = 2;
//                    FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment(savedInstanceState);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initPhoneCallListener();

        //默认分辨率 720
        if (PttApplication.getInstance().getDefVideoSize() == -1) {
            PttApplication.getInstance().setVideoSize(2);
            T01Helper.getInstance().getSetEngine().setVideoCallInCallQuality(PttApplication.getInstance().getDefVideoSize());
        }else {
            T01Helper.getInstance().getSetEngine().setVideoCallInCallQuality(PttApplication.getInstance().getDefVideoSize());
        }
    }

    private void initFragment(Bundle savedInstanceState) {
        IntercomFragment intercomFragment = null;
        if (savedInstanceState == null) {
            intercomFragment = new IntercomFragment();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            intercomFragment = (IntercomFragment) FragmentUtils.findFragment(fm, IntercomFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(intercomFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.fragment, 0);
    }

    public void initPhoneCallListener() {
        T01Helper.getInstance().getCallEngine().setCallReceiverListener(new CallEngine.CallEventCallBack() {
            public void onCallMeetingComing(int i, String call_name, String s1) {
                Intent intent = null;
                switch (i) {
                    case 3:
                        //语音会议
                        intent = new Intent(MainActivity.this, TestCallActivity.class);
                        intent.putExtra("callName", "语音会议：");
//			intent.setClass(context, CallActivity.class);
                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
                                s1);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, TestCallActivity.class);
                        intent.putExtra("callName", "视频会议：");
//			intent.setClass(context, CallActivity.class);
                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
                                s1);

                        break;
                    default:
                        break;
                }
                MainActivity.this.startActivity(intent);
//                EventBus.getDefault().post("", EventBusTags.UPDATA_MEG);
            }

            @Override
            public void onCallOutComing(int i, String call_name, String s1) {
                Intent intent = null;
                switch (i) {
                    case 1:
                        intent = new Intent(MainActivity.this, TestCallActivity.class);
                        intent.putExtra("callName", call_name);
                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
                                s1);
                        break;
                    case 2: //视频呼叫
                        intent = new Intent(MainActivity.this, TestCallActivity.class);
                        intent.putExtra("callName", call_name);
                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
                                s1);
                        break;
                    default:
                        break;
                }
                MainActivity.this.startActivity(intent);
            }

            @Override
            public void onCallInComing(int i, String sessionId) {
                Intent call = null;
                switch (i) {
                    case 1: //语音来电
                        call = new Intent(MainActivity.this, TestCallActivity.class);
                        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        call.putExtra(SipEventReceiver.SESSION_ID_PARAM,
                                sessionId);
                        MainActivity.this.startActivity(call);
                        break;
                    case 2: //代表视频来电
                        call = new Intent(MainActivity.this, TestCallActivity.class);
                        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        call.putExtra(SipEventReceiver.SESSION_ID_PARAM,
                                sessionId);
                        break;
                    default:
                        break;
                }
                MainActivity.this.startActivity(call);
            }

            @Override
            public void onVideoMonitor(int type, String name, String sessionId) {
         /*       Intent intent = new Intent(MainActivity.this, CallActivity.class);
                intent.putExtra("callName", name);
                intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
                        sessionId);
                intent.putExtra(LYConstants.VOIP_STATE_VIDEOMONITOR, 0x3);
                MainActivity.this.startActivity(intent);*/
            }
        });


    }

}
