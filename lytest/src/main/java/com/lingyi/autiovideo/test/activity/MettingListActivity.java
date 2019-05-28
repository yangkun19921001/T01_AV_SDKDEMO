package com.lingyi.autiovideo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.MeetingEngine;
import com.bnc.activity.entity.MeetingSessionEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.adapter.MettingAdapter;
import com.lingyi.autiovideo.test.adapter.VoipContactEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangk on 2018/1/10.
 */

public class MettingListActivity extends AppCompatActivity {

    private static final String TAG = "MettingListActivity";
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
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.pro)
    ProgressBar pro;
    private MettingAdapter mMettingAdapter;

    private ArrayList<MeetingSessionEntity> memberList = new ArrayList<>();
    private ArrayList<Integer> integers = new ArrayList<>();

    private ArrayList<VoipContactEntity> voipContactEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metting);
        ButterKnife.bind(this);

        initView();

        initListener();


    }

    private void initView() {
        tvAlarmmore.setText("添加会议");
        toolbarTitle.setText("会议列表");
        toolbarBack.setVisibility(View.GONE);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMettingAdapter = new MettingAdapter(this, null);
        mRecyclerView.setAdapter(mMettingAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();


    }

    private void initListener() {
        tvAlarmmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MettingListActivity.this, CreateGroupActivity.class);
                intent.putExtra(getString(R.string.Select_User), 1);
                startActivity(intent);
            }
        });

        mMettingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MeetingSessionEntity meetingSessionEntity = memberList.get(i);
                ArrayList<com.bnc.activity.entity.VoipContactEntity> members = meetingSessionEntity.getMembers();
                voipContactEntities.clear();
                integers.clear();
                for (com.bnc.activity.entity.VoipContactEntity list : members
                        ) {
                    VoipContactEntity voipContactEntity = new VoipContactEntity();
                    voipContactEntity.setId(list.getId());
                    voipContactEntity.setCall_state(list.getCall_state());
                    voipContactEntity.setCall_time(list.getCall_time());
                    voipContactEntity.setCall_type(list.getCall_type());
                    voipContactEntity.setLetterName(list.getLetterName());
                    voipContactEntity.setName(list.getName());
                    voipContactEntity.setNumber(list.getNumber());
                    voipContactEntities.add(voipContactEntity);
                    integers.add(Integer.parseInt(list.getNumber()));
                }

                if (voipContactEntities.size() == 0 )return;
                Intent intent = new Intent(MettingListActivity.this, MettingGroupMemberActivity.class);
                intent.putParcelableArrayListExtra("MettingGroupMember", voipContactEntities);
                intent.putExtra("MettingThem", meetingSessionEntity.getTheme());
                if (integers != null && integers.size() > 0)
                    intent.putIntegerArrayListExtra("MettingMember", integers);
                startActivity(intent);
            }
        });
    }


    private void initData() {
        T01Helper.getInstance().getGroupEngine().getMeetingList(new MeetingEngine.MeetingCallBack() {
            @Override
            public void getMeetingLists(ArrayList<MeetingSessionEntity> arrayList) {
                memberList.clear();
                memberList.addAll(arrayList);
                Log.i(TAG, arrayList + "");
                if (arrayList.size() == 0) {
                    ToastUtils.showShort("会议列表为空");
                }
                mMettingAdapter.setNewData(memberList);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onGetMeetingLoading() {
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCreateError(String s) {
                ToastUtils.showShort(s);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onGetMettingSuccess() {

            }
        });
    }

    @OnClick(R.id.edit_btn)
    public void onViewClicked() {
    }
}
