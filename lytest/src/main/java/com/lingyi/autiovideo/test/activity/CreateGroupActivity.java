package com.lingyi.autiovideo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.callback.ICreateTempListener;
import com.bnc.activity.entity.GroupEntity;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.adapter.CreateMettingGroupAdapter;
import com.lingyi.autiovideo.test.adapter.VoipContactEntity;
import com.lingyi.autiovideo.test.widget.popup.MDDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangk on 2018/1/10.
 */

public class CreateGroupActivity extends AppCompatActivity {
    private static final String TAG = "CreateGroupActivity";
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
    private CreateMettingGroupAdapter mCreateMettingGroupAdapter;
    private int open_type;

    private ArrayList<Integer> tempList = new ArrayList<>();
    private EditText et_group_name;
    private EditText et_group_name1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);

        initView();

        initListener();

        initData();
    }

    private void initView() {
        toolbarBack.setVisibility(View.GONE);
        editBtn.setVisibility(View.VISIBLE);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCreateMettingGroupAdapter = new CreateMettingGroupAdapter(this, null);
        mRecyclerView.setAdapter(mCreateMettingGroupAdapter);
        if (null != getIntent().getExtras() && getIntent().getExtras().getInt(getString(R.string.Select_User), -1) != -1) {
            open_type = getIntent().getExtras().getInt(getString(R.string.Select_User), -1);
            toolbarTitle.setText(open_type == 1 ? "创建会议" : "创建对讲组");
            tvAlarmmore.setText("确认创建");
        }
    }

    private void initListener() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(View.VISIBLE);
                final ArrayList<VoipContactEntity> selectUserList = mCreateMettingGroupAdapter.getSelectUserList();
                if (selectUserList != null || selectUserList.size() > 0) {
                    new MDDialog.Builder(CreateGroupActivity.this)
                            .setContentView(R.layout.layout_create_group)
                            .setContentViewOperator(new MDDialog.ContentViewOperator() {
                                @Override
                                public void operate(View contentView) {
                                    et_group_name = (EditText) contentView.findViewById(R.id.et_group_name);
                                }
                            }).setTitle("主题名称")
                            .setPositiveButtonMultiListener(new MDDialog.OnMultiClickListener() {

                                @Override
                                public void onClick(View clickedView, View contentView) {
                                    if (et_group_name.getText().toString().trim().isEmpty()) {
                                        ToastUtils.showShort("填写组名！");
                                        return;
                                    }
                                    if (open_type == 1) { //创建会议组
                                        ArrayList<Integer> integers = handleCreateTempGroup(selectUserList);
                                        Intent intent = new Intent(CreateGroupActivity.this, MettingGroupMemberActivity.class);
                                        intent.putParcelableArrayListExtra("MettingGroupMember", selectUserList);
                                        intent.putExtra("MettingThem", et_group_name.getText().toString().trim());
                                        if (integers != null && integers.size() > 0)
                                            intent.putIntegerArrayListExtra("MettingMember", integers);
                                        startActivity(intent);
                                        finish();
                                    } else { //创建对讲组
                                        createTemp(et_group_name.getText().toString().trim(), selectUserList);
                                    }


                                }
                            }).setNegativeButtonMultiListener(new MDDialog.OnMultiClickListener() {
                        @Override
                        public void onClick(View clickedView, View contentView) {
                            pro.setVisibility(View.GONE);
                        }
                    })
                            .setWidthMaxDp(600)
//               .setShowTitle(false)
                            .setShowButtons(true)
                            .create()
                            .show();


                } else {
                    ToastUtils.showShort("请选择添加的用户");
                }
            }
        });

        mCreateMettingGroupAdapter.setGroupMemberChange(new CreateMettingGroupAdapter.IGroupMemberChangeListener()

        {
            @Override
            public void onSelectMemberChange(int count) {
                if (count == 0) {
                    toolbarTitle.setText(open_type == 1 ? "创建会议" : "创建对讲组");
                } else {
                    toolbarTitle.setText(open_type == 1 ? "创建会议" + count + "/16" : "创建对讲组" + count + "/16");
                }

            }
        });
    }

    private void createTemp(String name, ArrayList<VoipContactEntity> selectUserList) {
        ArrayList<Integer> integers = handleCreateTempGroup(selectUserList);
        if (integers != null && integers.size() > 0) {
            T01Helper.getInstance().getPttEngine().createTempPttGroup(name, integers, new ICreateTempListener() {
                @Override
                public void onCreateError(String error) {
                    ToastUtils.showShort(error);
                    pro.setVisibility(View.GONE);
                }

                @Override
                public void onCreateSucceed() {
                    pro.setVisibility(View.GONE);
                    ToastUtils.showShort("成功");
                    finish();
                }

                @Override
                public void updateUsers() {
                    pro.setVisibility(View.GONE);
                }

                @Override
                public void updateGroups() {
                    pro.setVisibility(View.GONE);
                }

                @Override
                public void updateUnits() {
                    pro.setVisibility(View.GONE);
                }
            });
        }
    }

    private ArrayList<Integer> handleCreateTempGroup(ArrayList<VoipContactEntity> selectUserList) {
        tempList.clear();
        for (VoipContactEntity number : selectUserList) {
            if (number.getId() != 0) {
                tempList.add(number.getId());
            }
        }
        return tempList;
    }

    private void initData() {
        List<GroupEntity> allGroup = T01Helper.getInstance().getPttEngine().getAllGroup();
        for (GroupEntity group : allGroup
                ) {
            com.lingyi.autiovideo.test.adapter.GroupEntity groupEntity = new com.lingyi.autiovideo.test.adapter.GroupEntity();
            groupEntity.setGroupId(group.getGroupId());
            groupEntity.setGroupName(group.getGroupName());
            groupEntity.setGroupPriority(group.getGroupPriority());
            groupEntity.setGroupState(group.getGroupState());
            groupEntity.setGroupType(group.getGroupType());
            groupEntity.setOnLineState(group.getOnLineState());
            groupEntity.setLetterName(group.getLetterName());
            groupEntity.setOperation(group.getOperation());
            groupEntity.setUnitId(group.getUnitId());
            mCreateMettingGroupAdapter.addData(groupEntity);
        }
        mCreateMettingGroupAdapter.notifyDataSetChanged();
        pro.setVisibility(View.GONE);
    }
}
