package com.lingyi.autiovideo.test.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.blankj.ALog;
import com.bnc.activity.service.db.DataDao;
import com.bnc.activity.view.manager.UserListManager;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.activity.CreateGroupActivity;
import com.lingyi.autiovideo.test.model.GroupEntity;
import com.lingyi.autiovideo.test.model.UserEntity;
import com.lingyi.autiovideo.test.model.VoipContactEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yangk on 2017/12/22.
 */

public class CreateMettingGroupAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> implements UserListManager.CallViewBack1 {
    private int GROUP = 1;
    private int GROUP_MEMBER = 2;

    private Boolean isAdd = false;

    /**
     * 点击的条目索引
     */
    private int currentIndex = 0;
    private GroupEntity groupEntity;
    private int current_pos;
    private ArrayList<VoipContactEntity> grpIdList;
    private IGroupMemberChangeListener iGroupMemberChangeListener;


    public CreateMettingGroupAdapter(CreateGroupActivity createGroupActivity, List<MultiItemEntity> data) {
        super(data);
        addItemType(1, R.layout.adapter_metting_group);
        addItemType(2, R.layout.adapter_metting_group_member);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        switch (baseViewHolder.getItemViewType()) {
            case 1://显示组
                showGroup(baseViewHolder, multiItemEntity);
                break;
            case 2://显示组员
                showGroupMember(baseViewHolder, multiItemEntity);
                break;
            default:
                break;
        }
    }

    private void showGroupMember(final BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        UserEntity userEntity = (UserEntity) multiItemEntity;
        baseViewHolder.setText(R.id.tv_name, userEntity.getUserName());
        final CheckBox checkBox = baseViewHolder.getView(R.id.cb);
        baseViewHolder.getView(R.id.cb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity entity = (UserEntity) getData().get(baseViewHolder.getPosition());
                if (checkBox.isChecked()) {
                    addGroupId(entity.getUserId(), entity.getUserName());
                    entity.setSelectUser(true);
                } else {
                    deleteGroupId(entity.getUserId());
                    entity.setSelectUser(false);
                }
            }
        });


        if (userEntity.isSelectUser()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    private void showGroup(final BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        groupEntity = (GroupEntity) multiItemEntity;
        baseViewHolder.setText(R.id.tv_name, groupEntity.getGroupName())
                .setText(R.id.tv_content, groupEntity.getGroupId() + "")
                .setImageResource(R.id.iv, groupEntity.isExpanded() ? R.drawable.ic_expand_less_black_24dp : R.drawable.ic_expand_more_black_24dp)
        ;

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_pos = baseViewHolder.getAdapterPosition();
                GroupEntity group_Entity = (GroupEntity) getData().get(current_pos);
                if (current_pos != currentIndex) {
                    currentIndex = 0;
                }
                if (group_Entity.isExpanded()) {
                    collapse(current_pos);
                } else {
                    if (group_Entity.isExpand()) {
                        expand(current_pos);
                    } else {
                        GroupEntity multiItemEntity = null;
                        DataDao daoData = DataDao.getInstance();
                        List<com.bnc.activity.entity.UserEntity> userByGroupId = daoData.findUserByGroupId(group_Entity.getGroupId());
                        if (userByGroupId != null && userByGroupId.size() > 0) {
                            for (com.bnc.activity.entity.UserEntity user : userByGroupId) {
                                UserEntity userEntity = new UserEntity();
                                userEntity.setLetterName(user.getLetterName());
                                userEntity.setUserAvailableState(user.getUserAvailableState());
                                userEntity.setUserId(user.getUserId());
                                userEntity.setUserName(user.getUserName());
                                userEntity.setUserPriority(user.getUserPriority());
                                userEntity.setUserState(user.getUserState());
                                userEntity.setUserStyle(user.getUserStyle());
                                multiItemEntity = (GroupEntity) getData().get(current_pos);
                                multiItemEntity.addSubItem(userEntity);
                                multiItemEntity.setExpand(true);
                            }
                        }
                        mData.add(multiItemEntity);
                        mData.remove(mData.size() - 1);
                        expand(current_pos);
                        GroupEntity groupEntity = (GroupEntity) getData().get(current_pos);
                        groupEntity.setExpand(true);
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void refreshUserListView() {
    }

    @Override
    public void refreshVoipLoginState(int loginState, String reason) {

    }

    /***
     * 废弃
     */
    public void setCllBack() {
        try {
            UserListManager instance = UserListManager.getInstance();
            instance.setCallViewBack1(CreateMettingGroupAdapter.this);
        } catch (Exception e) {
            ALog.e(e.getMessage());
        }
    }


    /**
     * 添加选中组到列表
     *
     * @param Id
     * @param userName
     */
    private void addGroupId(int Id, String userName) {
        if (grpIdList == null) {
            grpIdList = new ArrayList<VoipContactEntity>();
            grpIdList.clear();
        }
        Iterator<VoipContactEntity> it = grpIdList.iterator();
        while (it.hasNext()) {
            VoipContactEntity voipContactEntity = it.next();
            if (voipContactEntity.getId() == Id)
                return;
        }
        VoipContactEntity voipContactEntity = new VoipContactEntity();
        voipContactEntity.setId(Id);
        voipContactEntity.setName(userName);
        voipContactEntity.setNumber(Id+"");
        grpIdList.add(voipContactEntity);

        if (iGroupMemberChangeListener != null){
            iGroupMemberChangeListener.onSelectMemberChange(grpIdList.size());
        }
    }

    /**
     * 从列表删除选中组
     *
     * @param groupId
     */
    private void deleteGroupId(int groupId) {
        if (grpIdList == null)
            return;
        Iterator<VoipContactEntity> it = grpIdList.iterator();
        while (it.hasNext()) {
            VoipContactEntity voipContactEntity = it.next();
            if (voipContactEntity.getId() == groupId)
                it.remove();
        }
        if (iGroupMemberChangeListener != null){
            iGroupMemberChangeListener.onSelectMemberChange(grpIdList.size());
        }

    }

    public ArrayList<VoipContactEntity> getSelectUserList() {
        return grpIdList;
    }

    public  interface IGroupMemberChangeListener{
        void onSelectMemberChange(int count);
    }

    public void setGroupMemberChange(IGroupMemberChangeListener iGroupMemberChangeListener){
        this.iGroupMemberChangeListener = iGroupMemberChangeListener;
    }
}
