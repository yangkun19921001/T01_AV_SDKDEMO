package com.lingyi.autiovideo.test.widget.popup;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.utils.ViewType;
import com.bnc.activity.widget.call.CallLayout;
import com.lingyi.autiovideo.test.R;

import org.doubango.ngn.sip.NgnInviteSession;

/**
 * Created by yangk on 2018/12/10.
 */

public class MyCallLayout extends CallLayout implements View.OnClickListener {
    private NgnInviteSession.InviteState mAVState;
    private TextView mTvInfo;
    private TextView tvRemoteNumber;
    private TextView tryingRemoteName;

    public MyCallLayout(Context context) {
        super(context);
        initCall();
    }

    public MyCallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCall();
    }


    public MyCallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initCall();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onCallDestory();
    }

    /**
     * 找到布局
     */
    private void initCall() {
        addCallLayoutListener(new ICallLayoutCallBack() {
            @Override
            public void findById(View view, LayoutState type) {
                switch (type) {
                    case DEF_LAYOUT:
                        handleDef(view);
                        break;
                    case AUDIO_LAYOUT:
                        handleAudio(view);
                        break;
                    case VIDEO_LAYOUT:
                        handleVideo(view);
                        break;
                    case TERMINATED:
                        handleTerminated(view);
                        break;
                }
            }

            @Override
            public void onError(String meg) {
                ToastUtils.showShort(meg);
            }
        });
    }

    /**
     * 处理结束中的业务
     * @param view
     */
    private void handleTerminated(View view) {
        mTvInfo = (TextView) view
                .findViewById(R.id.tryingPhoneAttribution);
        mTvInfo.setText(getContext().getString(R.string.string_call_terminated)
               );
        if (mCurrentView == ViewType.ViewTermwait) {
            return;
        }
        final TextView tvRemote = (TextView) view
                .findViewById(R.id.tryingRemoteNumber);
        tryingRemoteName = (TextView) view
               .findViewById(R.id.tryingRemoteName);
        view.findViewById(R.id.tryAnswer).setVisibility(View.GONE);
        view.findViewById(R.id.tryHangup).setVisibility(View.GONE);
        // 显示号码
        if (mRemotePartyDisplayName != null && tvRemoteNumber != null) {
            Log.e(TAG, "xianshi" + mRemotePartyDisplayName);
            tvRemoteNumber.setText("(" + mRemotePartyDisplayName + ")");
        }
        // 显示名称
        if (!TextUtils.isEmpty(mUserName.getUserName())) {
            tryingRemoteName.setText(mUserName.getUserName());
        } else {
            tryingRemoteName.setText(mUserName.getUserId());
        }
        tvRemote.setText("(" + mRemotePartyDisplayName + ")");
    }

    /**
     * 处理视频通话的业务
     * @param view
     */
    private void handleVideo(View view) {

        //解决 硬编花屏问题
        if (T01Helper.getInstance().getSetEngine().getCurVideoQuality().equals("高清") ||
                T01Helper.getInstance().getSetEngine().getCurVideoQuality().equals("超清")) {
            ScreenUtils.setLandscape(mContext.get());
        }

        view.findViewById(R.id.video_call_mute_btn).setOnClickListener(MyCallLayout.this);
        view.findViewById(R.id.video_call_handsfree_btn).setOnClickListener(MyCallLayout.this);
        view.findViewById(R.id.video_call_local_btn).setOnClickListener(MyCallLayout.this);
        view.findViewById(R.id.video_call_change_btn).setOnClickListener(MyCallLayout.this);
        view.findViewById(R.id.video_call_hangup).setOnClickListener(MyCallLayout.this);
        final TextView tvRemote = (TextView) view
                .findViewById(R.id.video_call_number);
        if (!TextUtils.isEmpty(mUserName.getUserName())) {
            tvRemote.setText(mUserName.getUserName());
        } else {
            tvRemote.setText(mUserName.getUserId());
        }
    }


    /**
     * 处理音频通话的业务
     * @param view
     */
    private void handleAudio(View view) {
        view.findViewById(R.id.audio_call_hangup).setOnClickListener(MyCallLayout.this);
        view.findViewById(R.id.audio_call_mute_btn).setOnClickListener(MyCallLayout.this);
        view.findViewById(R.id.audio_call_handsfree_btn).setOnClickListener(MyCallLayout.this);

        final TextView tvRemote = (TextView) view
                .findViewById(R.id.audio_call_number);
        final Button btHang = (Button) view
                .findViewById(R.id.audio_call_hangup);
        if (!TextUtils.isEmpty(mUserName.getUserName())) {
            tvRemote.setText(mUserName.getUserName());
        } else {
            tvRemote.setText(mUserName.getUserId());
        }
    }

    /**
     * 处理默认通话的布局中的业务
     * @param view
     */
    private void handleDef(View view) {
        view.findViewById(R.id.tryAnswer).setOnClickListener(MyCallLayout.this);
        view.findViewById(R.id.tryHangup).setOnClickListener(MyCallLayout.this);

        mTvInfo = (TextView) view.findViewById(R.id.tryingPhoneAttribution);
        tvRemoteNumber = (TextView) view.findViewById(R.id.tryingRemoteNumber);
        tryingRemoteName = (TextView) view.findViewById(R.id.tryingRemoteName);

        final Button btAnswer = (Button) view.findViewById(R.id.tryAnswer);
        final Button btHang = (Button) view.findViewById(R.id.tryHangup);
        final ImageView ivAvatar = (ImageView) view.findViewById(R.id.tryingContachPhone);

        switch (mAVState) {
            case INCOMING:
                mTvInfo.setText(getContext().getString(R.string.string_call_incoming));
                break;
            case INPROGRESS:
                mTvInfo.setText(getContext().getString(R.string.string_call_inprogress));
                btAnswer.setVisibility(View.GONE);
                break;
            case INCALL:
                mTvInfo.setText(getContext().getString(R.string.string_call_outgoing));
                break;
            case REMOTE_RINGING:
            case EARLY_MEDIA:
            default:
                mTvInfo.setText(getContext().getString(R.string.string_call_inprogress));
                break;
        }

        if (mRemotePartyDisplayName != null && tvRemoteNumber != null)
            // 显示号码
            tvRemoteNumber.setText("(" + mRemotePartyDisplayName + ")");
        // 显示名称
        // 显示名称
        if (!TextUtils.isEmpty(mUserName.getUserName())) {
            tryingRemoteName.setText(mUserName.getUserName());
        } else {
            tryingRemoteName.setText(mUserName.getUserId());
        }

        if ((mSessionType & getTypeVideo()) == getTypeAudio()) {
            ivAvatar.setBackgroundResource(R.drawable.re_audio_center_bg_focus);
        } else if ((mSessionType & TYPE_VIDEO) == TYPE_VIDEO) {
            ivAvatar.setBackgroundResource(R.drawable.re_video_incoming);
        }
    }

    /**
     * 加载通话结束的布局
     *
     * @return
     */
    @Override
    protected int loadCallDestoryLayout() {
        return R.layout.activity_trying;
    }

    /**
     * 加载当前进入的默认布局
     *
     * @param state 当前的通话状态
     * @return
     */
    @Override
    public int loadDefCurrentLayout(NgnInviteSession.InviteState state) {
        mAVState = state;
        return R.layout.activity_trying;
    }

    /**
     * 音频通话的布局
     *
     * @param state
     * @return
     */
    @Override
    public int loadAudioLayout(NgnInviteSession.InviteState state) {
        return R.layout.activity_audio_call;
    }

    /**
     * 加载视频通话功能的布局
     *
     * @param state
     * @return
     */
    @Override
    protected int loadVideoFunctionLayout(NgnInviteSession.InviteState state) {
        return R.layout.layout_video_funchtion;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tryAnswer:
                T01Helper.getInstance().getCallEngine().acceptCall();
                break;
            case R.id.video_call_handsfree_btn:
            case R.id.audio_call_handsfree_btn:
                T01Helper.getInstance().getCallEngine().isHandsfree();
                break;
            case R.id.video_call_local_btn:
                T01Helper.getInstance().getCallEngine().stopLocalVideo();
                break;
            case R.id.video_call_mute_btn:
            case R.id.audio_call_mute_btn:
                T01Helper.getInstance().getCallEngine().isMute();
                break;
            case R.id.video_call_change_btn:
                T01Helper.getInstance().getCallEngine().changeCamera();
                break;
            case R.id.tryHangup:
            case R.id.audio_call_hangup:
            case R.id.video_call_hangup:
                T01Helper.getInstance().getCallEngine().hangUpCall();
                break;
        }
    }
}
