package com.lingyi.autiovideo.test.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.ALog;
import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.engine.PttEngine;
import com.bnc.activity.entity.GroupEntity;
import com.bnc.activity.entity.UserEntity;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.activity.CreateGroupActivity;
import com.lingyi.autiovideo.test.activity.MettingListActivity;
import com.lingyi.autiovideo.test.adapter.CurrentDepartmentAdapter;
import com.lingyi.autiovideo.test.widget.popup.MenuItem;
import com.lingyi.autiovideo.test.widget.popup.TopRightMenu;

import org.doubango.ngn.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangk on 2018/1/5.
 */

public class IntercomFragment extends BaseFragment{
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_start_intercom)
    LinearLayout ll_start_intercom;
    @BindView(R.id.edit_btn)
    LinearLayout edit_btn;
    @BindView(R.id.pro)
    ProgressBar pro;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.tv_metting)
    TextView tv_metting;
    private boolean isTalking;
    private CurrentDepartmentAdapter mCurrentDepartmentAdapter;
    private List<MenuItem> menuItems = new ArrayList<>();
    private ArrayList<GroupEntity> lists;

    @Override
    public int getLayout() {
        return R.layout.fragment_intercom;
    }

    @Override
    protected void initData() {
        toolbar_title.setText("对讲组");
        initRecyclerView();

        setItemListener();

    }

    @Override
    public void onResume() {
        super.onResume();
        T01Helper.getInstance().getPttEngine().getCurrentPttGroup(new PttEngine.PttListCallBack() {
            @Override
            public void getCurrentPttLists(ArrayList<UserEntity> arrayList) {
                try {
                    pro.setVisibility(View.GONE);
                    ALog.i(
                            TAG,
                            "refreshUserListView() --> " + "callUserId - "
                                    + Constants.callUserId + " targetUserId - "
                                    + Constants.targetUserId + " callType - "
                                    + Constants.callType);
                    mCurrentDepartmentAdapter.setUserList(arrayList);
                    ArrayList<UserEntity> userList = mCurrentDepartmentAdapter.getUserList();
                    if (userList != null && userList.size() > 0)
                        mCurrentDepartmentAdapter.setNewData(userList);
                    mCurrentDepartmentAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    ALog.e(e.getMessage());
                }
            }

            @Override
            public void getAllPttLists(ArrayList<GroupEntity> arrayList) {

            }

            @Override
            public void voipLoginState(String s) {
                ToastUtils.showLong(s);
            }
        });
        getAllGroup();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopRightMenu mTopRightMenu = new TopRightMenu(getActivity());
                mTopRightMenu
//                        .setHeight(480)     //默认高度480
//                        .setWidth(320)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0){
                                    Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                                    intent.putExtra(getString(R.string.Select_User), 2);
                                    startActivity(intent);
                                    return;
                                }
                                if (menuItems != null && menuItems.size() > 0 && lists != null && menuItems.size() > 0) {
                                    T01Helper.getInstance().getPttEngine().setCurrentPttGroup(lists.get(position-1).getGroupId());
                                } else {
                                    ToastUtils.showShort("没有查找到对讲组");
                                }

                            }
                        })
                        .showAsDropDown(edit_btn, -edit_btn.getWidth() - 50, 10);
            }
        });


        ll_start_intercom.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.ll_start_intercom:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Log.e(TAG, "MotionEvent.ACTION_DOWN");
                            if (!isTalking()) {
                                // 开始对讲
                                isTalking = true;
                                T01Helper.getInstance().getPttEngine().startPttGroup();
                                ll_start_intercom.setBackground(getActivity().getDrawable(R.drawable.circle_gre_decorator));
                            }
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            // 停止对讲
                            // Log.e(TAG, "talk button up");
                            isTalking = false;
                            T01Helper.getInstance().getPttEngine().stopPttGroup();
                            ll_start_intercom.setBackground(getActivity().getDrawable(R.drawable.circle_decorator));
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                            ALog.i(TAG, "onTouch MotionEvent.ACTION_MOVE");

                        }
                        return true;
                    default:
                        return false;
                }
            }
        });

        tv_metting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MettingListActivity.class));
            }
        });
    }

    private void getAllGroup() {
        List<GroupEntity> allGroup = T01Helper.getInstance().getPttEngine().getAllGroup();
        lists = (ArrayList<GroupEntity>) allGroup;
        menuItems.clear();
        menuItems.add(new MenuItem("#创建临时组#"));
        if (allGroup != null && allGroup.size() > 0) {
            for (GroupEntity group : allGroup) {
                menuItems.add(new MenuItem(group.getGroupName()));
                if (Constants.curGroupId == group.getGroupId()) {
                    toolbar_title.setText(group.getGroupName());
                }
            }
        }
    }


    /**
     * 返回当前对讲按钮状态
     *
     * @return
     */
    public boolean isTalking() {
        return isTalking;
    }

    private void initRecyclerView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mCurrentDepartmentAdapter = new CurrentDepartmentAdapter(null);
        mRecyclerView.setAdapter(mCurrentDepartmentAdapter);
    }



    public void setItemListener() {
        mCurrentDepartmentAdapter.setItemClickListener(new CurrentDepartmentAdapter.ItemClickListener() {
            @Override
            public void voipVideoCall(UserEntity userEntity) {
                if (T01Helper.getInstance().getCallEngine().canCallAudioVideo(userEntity.getUserState())) {
                    T01Helper.getInstance().getCallEngine().call(userEntity.getUserId() + "",Constants.IVoipLaunchType.VOIP_LAUNCH_TYPE_VIDEO,  userEntity.getUserName());
                }
            }

            @Override
            public void voipVoiceCall(UserEntity userEntity) {
                if (T01Helper.getInstance().getCallEngine().canCallAudioVideo(userEntity.getUserState())) {
                    T01Helper.getInstance().getCallEngine().call(userEntity.getUserId() + "",Constants.IVoipLaunchType.VOIP_LAUNCH_TYPE_TELE,  userEntity.getUserName());

                }
            }
        });
    }
}