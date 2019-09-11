package com.lingyi.autiovideo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.PttApplication;
import com.bnc.activity.T01Helper;
import com.bnc.activity.callback.IShowNotityCallBack;
import com.bnc.activity.engine.CALL_TYPE;
import com.bnc.activity.engine.CallEngine;
import com.bnc.activity.engine.RegisterEngine;
import com.bnc.activity.entity.MsgMessageEntity;
import com.bnc.activity.entity.UserEntity;
import com.bnc.activity.service.db.DataDao;
import com.bnc.activity.utils.LogHelper;
import com.bnc.activity.utils.PropertyUtil;
import com.bnc.activity.utils.UserAndGroupCacheMap;
import com.bnc.activity.view.manager.UnitManager;
import com.lingyi.autiovideo.test.Constants;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.fragment.ChatFragment;
import com.lingyi.autiovideo.test.fragment.IntercomFragment;
import com.lingyi.autiovideo.test.utils.FragmentUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;

import org.doubango.ngn.sip.NgnAVSession;
import org.doubango.ngn.utils.NgnAVSessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

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
                    FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
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
//        initPhoneCallListener();

        initPhoneCallListenerTest();

        //默认分辨率 720
        if (PttApplication.getInstance().getDefVideoSize() == -1) {
            PttApplication.getInstance().setVideoSize(2);
            T01Helper.getInstance().getSetEngine().setVideoCallInCallQuality(PttApplication.getInstance().getDefVideoSize());
        } else {
            T01Helper.getInstance().getSetEngine().setVideoCallInCallQuality(PttApplication.getInstance().getDefVideoSize());
        }

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });


        /*获取所有组织*/
        getAllUnitList();

        /*获取联系人*/
        getUserContactsList();

        /*获取外部设备*/
        getWBUserList();

        /*获取GPS定位*/
        getGPSInfoList();
        /**是否在线*/
        isRegister();
        //请求在线人数
        requestOnlineUser();
        //后台消息监听
        addMessageListener();

    }

    private void addMessageListener() {
        T01Helper.getInstance().getMessageEngine().showMegToNotity(new IShowNotityCallBack() {
            @Override
            public void getShowMeg(MsgMessageEntity msgMessageEntity) {
                ToastUtils.showShort("后台收到消息：" + msgMessageEntity.toString());
            }
        });
    }

    private void sendBroadcast(String action, long id, int call_type) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(Constants.SESSION_ID, id);
        intent.putExtra(Constants.CALL_TYPE, call_type);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void requestOnlineUser() {
        T01Helper.getInstance().getContactsEngine().getOnlineUserCallBack(5000, new UnitManager.IOnLineUserListener() {
            @Override
            public void onGetSuccee(List<UserEntity> list) {
                Log.i(TAG, "轮训获取在线用户列表" + list);

            }

            @Override
            public void onGetSuccee(String json) {

            }

            @Override
            public void onGetError(String s) {

            }
        });
    }

    private void isRegister() {
        boolean registerEngine = T01Helper.getInstance().getRegisterEngine().isRegister();
        Log.e(TAG, "当前用户是否在线" + registerEngine);
    }

    private void getGPSInfoList() {
        T01Helper.getInstance().getContactsEngine().getGPSInfoList(new UnitManager.IGPSListener() {
            @Override
            public void onGetSuccee(String meg) {
                Log.i(TAG, "当前GPS--onGetSuccee" + meg);
            }

            @Override
            public void onGetError(String meg) {
                Log.i(TAG, "当前GPS--onGetSuccee" + meg);

            }
        });
    }

    private void getWBUserList() {
        T01Helper.getInstance().getContactsEngine().getWBUserList(new UnitManager.IWBUserListener() {
            @Override
            public void onGetSuccee(String meg) {
                Log.i(TAG, "当前WB联系人--onGetSuccee" + meg);
            }

            @Override
            public void onGetError(String meg) {
                Log.i(TAG, "当前WB联系人--onGetSuccee" + meg);

            }
        });
    }

    private void getUserContactsList() {
        T01Helper.getInstance().getContactsEngine().getUserList(new UnitManager.IUserListener() {
            @Override
            public void onGetSuccee(String meg) {
                Log.i(TAG, "当前联系人--onGetSuccee" + meg);
            }

            @Override
            public void onGetError(String meg) {
                Log.i(TAG, "当前联系人--onGetSuccee" + meg);

            }
        });
    }

    public String getCurrentUnitId() {
        return PropertyUtil.getInstance(PttApplication.getInstance().getApplicationContext()).getString("UNIT_ID", null);
    }

    private void getAllUnitList() {

        T01Helper.getInstance().getContactsEngine().getALLUnitList(getCurrentUnitId(), new UnitManager.IUnitListener() {
            @Override
            public void onGetSuccee(String meg) {
                Log.i(TAG, "当前所有组织--onGetSuccee" + meg);
            }

            @Override
            public void onGetError(String meg) {
                Log.i(TAG, "当前所有组织--onGetError" + meg);

            }
        });
    }

    private void initPhoneCallListenerTest() {
        T01Helper.getInstance().getCallEngine().addCallReceiverListener(new CallEngine.ICallEventCallBack() {
            @Override
            public void onCallMeetingComing(CALL_TYPE call_type, String meetingMember, NgnAVSession ngnAVSession) {
                switch (call_type) {
                    case VIDEO_MEETING:
                        Log.i(TAG, "视频会议" + ": meetingMember" + meetingMember);
                        break;
                    case AUDIO_MEETING:
                        Intent intent = new Intent(MainActivity.this, CallInOrOutActivity.class);
                        Log.i(TAG, "语音会议" + ": meetingMember" + meetingMember);
                        intent.putExtra(Constants.SESSION_ID, ngnAVSession.getId());
                        intent.putExtra(Constants.CALL_NUMBER, meetingMember);
                        intent.putExtra(Constants.CALL_TYPE, call_type.ordinal());
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onCallOutComing(CALL_TYPE call_type, String number, NgnAVSession ngnAVSession) {
                Intent intent = new Intent(MainActivity.this, CallInOrOutActivity.class);
                switch (call_type) {
                    case VIDEO_CALL_OUT:
                        Log.i(TAG, "视频呼出" + ": name" + number);
                        intent.putExtra(Constants.SESSION_ID, ngnAVSession.getId());
                        intent.putExtra(Constants.CALL_NUMBER, number);
                        intent.putExtra(Constants.CALL_TYPE, call_type.ordinal());
                        startActivity(intent);
                        break;
                    case AUDIO_CALL_OUT:
                        Log.i(TAG, "语音呼出" + ": name" + number);
                        intent.putExtra(Constants.SESSION_ID, ngnAVSession.getId());
                        intent.putExtra(Constants.CALL_NUMBER, number);
                        intent.putExtra(Constants.CALL_TYPE, call_type.ordinal());
                        startActivity(intent);
                        break;
                }

            }

            @Override
            public void onCallInComing(CALL_TYPE call_type, String number, NgnAVSession ngnAVSession) {
                Intent intent = new Intent(MainActivity.this, CallInOrOutActivity.class);
                if (!StringUtils.isEmpty(number)){
                    UserEntity userById = UserAndGroupCacheMap.getInstace().findUserById(Integer.parseInt(number));
                    Log.d(TAG,userById.toString());
                }
                switch (call_type) {
                    case AUDIO_CALL_IN:
                        Log.i(TAG, "语音来电" + ": name" + number);
                        intent.putExtra(Constants.SESSION_ID, ngnAVSession.getId());
                        intent.putExtra(Constants.CALL_NUMBER, number);
                        intent.putExtra(Constants.CALL_TYPE, call_type.ordinal());
                        startActivity(intent);
                        break;
                    case VIDEO_CALL_IN:
                        Log.i(TAG, "视频来电" + ": name" + number);
                        intent.putExtra(Constants.SESSION_ID, ngnAVSession.getId());
                        intent.putExtra(Constants.CALL_NUMBER, number);
                        intent.putExtra(Constants.CALL_TYPE, call_type.ordinal());
                        startActivity(intent);
                        break;
                }

            }


            @Override
            public void onVideoMonitor(CALL_TYPE call_type, String number) {
                switch (call_type) {
                    case VIDEO_MONITOR:
                        Log.i(TAG, "视频监控" + ": name" + number);
                        break;
                }
            }

            @Override
            public void onCallError(CALL_TYPE call_type, String error, NgnAVSession ngnAVSession) {
                Log.i(TAG, "呼叫失败" + ": error" + error);
                sendBroadcast(Constants.CALL_ACION, ngnAVSession.getId(), call_type.ordinal());

            }

            @Override
            public void onTerminated(CALL_TYPE call_type, String error, long sessionID) {
                Log.i(TAG, "通话结束");
                sendBroadcast(Constants.CALL_ACION, sessionID, call_type.ordinal());
            }

            /**
             * 默认一个
             */

            int sessionSize = 1;

            @Override
            public void onCallInCall(CALL_TYPE call_type, String number, NgnAVSession ngnAVSession) {
                sendBroadcast(Constants.CALL_IN_OR_OUR_ACION, ngnAVSession.getId(), call_type.ordinal());
                switch (call_type) {
                    case AUDIO_CALL_IN_CALL:
                        Log.i(TAG, "语音通话中" + number);
                        Intent intent = new Intent(MainActivity.this, AudioCallActivity.class);
                        intent.putExtra(Constants.SESSION_ID, ngnAVSession.getId());
                        intent.putExtra(Constants.CALL_NUMBER, number);
                        startActivity(intent);
                        break;
                    case VIDEO_CALL_IN_CALL:
                        Log.i(TAG, "视频通话中" + number);
                        //判断当前会话 ID 是否存在，并且当前线路 Size 大于上一次的线路 Size 就认为有新的线路加入。
                        // 或者根据自己当前所有显示的线路 < SDk 中的线路就认为有新加入的线路
                        if (ngnAVSession != null && T01Helper.getInstance().getCallEngine().isSessionAlive(ngnAVSession.getId()) &&
                                NgnAVSessionManager.getInstace().getNgnObservableHashMap().size() > sessionSize) {
                            //把当前线路加入到播放队列中就不需要再重新开启 Activity 了；
                            sendBroadcast(Constants.CALL_ACION, ngnAVSession.getId(), call_type.ordinal());
                            return;
                        }

                        Intent video_intent = new Intent(MainActivity.this, VideoCallActivity.class);
                        video_intent.putExtra(Constants.SESSION_ID, ngnAVSession.getId());
                        video_intent.putExtra(Constants.CALL_NUMBER, number);
                        startActivity(video_intent);
                        sessionSize = NgnAVSessionManager.getInstace().getNgnObservableHashMap().size();
                        break;
                }

            }
        });
    }

    private void initFragment(Bundle savedInstanceState) {
        IntercomFragment intercomFragment = null;
        ChatFragment chatFragment = null;
        if (savedInstanceState == null) {
            intercomFragment = new IntercomFragment();
            chatFragment = ChatFragment.getInstance();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            intercomFragment = (IntercomFragment) FragmentUtils.findFragment(fm, IntercomFragment.class);
            chatFragment = (ChatFragment) FragmentUtils.findFragment(fm, ChatFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(intercomFragment);
            mFragments.add(chatFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.fragment, 0);
    }

    public void initPhoneCallListener() {
//        T01Helper.getInstance().getCallEngine().setCallReceiverListener(new CallEngine.CallEventCallBack() {
//
//            public void onCallMeetingComing(int i, String call_name, String s1) {
//                Intent intent = null;
//                switch (i) {
//                    case 3:
//                        //语音会议
//                        intent = new Intent(MainActivity.this, TestCallActivity.class);
//                        intent.putExtra("callName", "语音会议：");
////			intent.setClass(context, CallActivity.class);
//                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
//                                s1);
//                        break;
//                    case 4:
//                        intent = new Intent(MainActivity.this, TestCallActivity.class);
//                        intent.putExtra("callName", "视频会议：");
////			intent.setClass(context, CallActivity.class);
//                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
//                                s1);
//
//                        break;
//                    default:
//                        break;
//                }
//                MainActivity.this.startActivity(intent);
////                EventBus.getDefault().post("", EventBusTags.UPDATA_MEG);
//            }
//
//            @Override
//            public void onCallOutComing(int i, String call_name, String s1) {
//                Intent intent = null;
//                switch (i) {
//                    case 1:
//                        intent = new Intent(MainActivity.this, TestCallActivity.class);
//                        intent.putExtra("callName", call_name);
//                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
//                                s1);
//                        break;
//                    case 2: //视频呼叫
//                        intent = new Intent(MainActivity.this, TestCallActivity.class);
//                        intent.putExtra("callName", call_name);
//                        intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
//                                s1);
//                        break;
//                    default:
//                        break;
//                }
//                MainActivity.this.startActivity(intent);
//            }
//
//            @Override
//            public void onCallInComing(int i, String sessionId) {
//                Intent call = null;
//                switch (i) {
//                    case 1: //语音来电
//                        call = new Intent(MainActivity.this, TestCallActivity.class);
//                        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        call.putExtra(SipEventReceiver.SESSION_ID_PARAM,
//                                sessionId);
//                        MainActivity.this.startActivity(call);
//                        break;
//                    case 2: //代表视频来电
//                        call = new Intent(MainActivity.this, TestCallActivity.class);
//                        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        call.putExtra(SipEventReceiver.SESSION_ID_PARAM,
//                                sessionId);
//                        break;
//                    default:
//                        break;
//                }
//                MainActivity.this.startActivity(call);
//            }
//
//            @Override
//            public void onVideoMonitor(int type, String name, String sessionId) {
//         /*       Intent intent = new Intent(MainActivity.this, CallActivity.class);
//                intent.putExtra("callName", name);
//                intent.putExtra(SipEventReceiver.SESSION_ID_PARAM,
//                        sessionId);
//                intent.putExtra(LYConstants.VOIP_STATE_VIDEOMONITOR, 0x3);
//                MainActivity.this.startActivity(intent);*/
//            }
//        });


    }

    public void test() {
        Intent video_intent = new Intent(MainActivity.this, VideoCallActivity.class);
        startActivity(video_intent);
    }

}
