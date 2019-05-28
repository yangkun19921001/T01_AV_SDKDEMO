package com.lingyi.autiovideo.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.MeetingEngine;
import com.bnc.activity.entity.MeetingSessionEntity;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.adapter.MettingDetailAdapter;
import com.lingyi.autiovideo.test.adapter.VoipContactEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangk on 2018/1/11.
 */

public class MettingGroupMemberActivity extends AppCompatActivity {
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
    private MettingDetailAdapter mettingDetailAdapter;
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
        mettingDetailAdapter = new MettingDetailAdapter(null);
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
//            LYHelper.getInstance().call("8858115", Constants.IVoipLaunchType.VOIP_LAUNCH_TYPE_TELECONFERENCE,"huiyi");
        T01Helper.getInstance().getGroupEngine().createMettingGroup(0, mettingThem, mettingMember, new MeetingEngine.MeetingCallBack() {
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
            public void onGetMettingSuccess() {

            }
        });
//              LYHelper.getInstance().callPhone("c885893", NgnMediaType.Audio,CallActivity.class);
        else
            ToastUtils.showShort("没有获取到会议人员，请重新进行添加！");
    }
}
