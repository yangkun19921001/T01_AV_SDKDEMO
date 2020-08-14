package com.lingyi.autiovideo.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.MeetingEngine;
import com.bnc.activity.entity.MeetingLaunchType;
import com.bnc.activity.entity.MeetingSessionEntity;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.adapter.MeetingDetailAdapter;
import com.lingyi.autiovideo.test.model.VoipContactEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangk on 2018/1/11.
 */

public class MeetingGroupMemberActivity extends AppCompatActivity {
    @BindView(R.id.tv_metting)
    TextView tvMetting;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_alarmmore)
    TextView tvAlarmmore;
    @BindView(R.id.edit_btn)
    LinearLayout editBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.et_meeting_topic)
    EditText etMeetingTopic;
    @BindView(R.id.layout_meeting_topic)
    LinearLayout mLayoutMeetingTopic;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_startMetting)
    TextView tvStartMetting;
    private MeetingDetailAdapter mettingDetailAdapter;
    private int meetingtype;
    private String mettingThem;
    private ArrayList<Integer> mettingMember;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metting_member_detail);
        ButterKnife.bind(this);


        initView();

        initListener();

        initData();
    }

    private void initView() {
        toolbarBack.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);
        toolbarTitle.setText("会议");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerview.setLayoutManager(gridLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mettingDetailAdapter = new MeetingDetailAdapter(null);
        mRecyclerview.setAdapter(mettingDetailAdapter);
        getMettingMember();
    }

    private void initListener() {

    }

    private void initData() {
    }

    public void getMettingMember() {
        this.toolbarTitle = toolbarTitle;
        if (getIntent() != null
                && getIntent().getParcelableArrayListExtra("MettingGroupMember") != null
                && getIntent().getIntegerArrayListExtra("MettingMember") != null
                && getIntent().getStringExtra("MettingThem") != null
                ) {
            ArrayList<VoipContactEntity> voipContactEntities = getIntent().getParcelableArrayListExtra("MettingGroupMember");
            if (voipContactEntities != null && voipContactEntities.size() > 0)
                mettingDetailAdapter.setNewData(voipContactEntities);
            mettingThem = getIntent().getStringExtra("MettingThem");
            mettingMember = getIntent().getIntegerArrayListExtra("MettingMember");
//            toolbarTitle.setText(meetingTheme);
            etMeetingTopic.setText(mettingThem);
        }

    }


    @OnClick(R.id.tv_startMetting)
    public void onViewClicked() {
        if (mettingMember != null)
        T01Helper.getInstance().getMeetingEngine().launchMeeting(MeetingLaunchType.VOIP_LAUNCH_TYPE_AUDIO_MEETING, mettingThem, mettingMember, new MeetingEngine.MeetingCallBack() {
            @Override
            public void getMeetingLists(ArrayList<MeetingSessionEntity> arrayList) {

            }

            @Override
            public void onGetMeetingLoading() {

            }

            @Override
            public void onCreateError(String s) {

            }

            @Override
            public void onGetMettingSuccess(String meetingID) {
                Log.i("launchMeeting",meetingID);
            }


        });
        else
            ToastUtils.showShort("没有获取到会议人员，请重新进行添加！");
    }
}
