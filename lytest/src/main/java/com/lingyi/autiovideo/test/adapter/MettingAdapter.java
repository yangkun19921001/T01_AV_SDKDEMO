package com.lingyi.autiovideo.test.adapter;

import com.bnc.activity.entity.MeetingSessionEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.activity.MettingListActivity;

import java.util.ArrayList;

/**
 * Created by yangk on 2017/12/21.
 */

public class MettingAdapter extends BaseQuickAdapter<MeetingSessionEntity, BaseViewHolder> {
    private int voiceCall = 0;
    private int videoCall = 1;


    public MettingAdapter(MettingListActivity mettingListActivity, ArrayList<MeetingSessionEntity> data) {
        super(R.layout.item_current_metting, data);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MeetingSessionEntity multiItemEntity) {
                baseViewHolder.setImageResource(R.id.user_icon,R.mipmap.voice_metting)
                        .setText(R.id.tv_name,multiItemEntity.getTheme())
                        .setText(R.id.tv_time,multiItemEntity.getDate())
                        .setText(R.id.tv_content,multiItemEntity.getConferenceId());
           /*     break;
            case 1: //视频会议
                baseViewHolder.setVisible(R.id.user_catalog,false)
                        .setVisible(R.id.cb,false)
                        .setImageResource(R.id.user_icon,R.mipmap.video_metting)
                        .setText(R.id.tv_name,multiItemEntity.getTheme())
                        .setText(R.id.tv_content,multiItemEntity.getConferenceId());
                break;*/

/*           baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (multiItemEntity.getMembers().size() <= 0) {
                       com.blankj.utilcode.util.ToastUtils.showShort(mContext.getString(R.string.metting_error));
                       return;
                   }

                *//*   Intent intent = new Intent(this,null);
                   ARouter.getInstance().build(ARouterPaths.MettingListToMettingMember)
                           .withParcelableArrayList(SpTag.MettingGroupMember,multiItemEntity.getMembers())
                           .withString(SpTag.ConferenceId,multiItemEntity.getConferenceId())
                           .withInt("openType",1)
                           .withString("theme", multiItemEntity.getTheme())
                           .withInt("compType", multiItemEntity.getCompType())
                           .withInt("size", multiItemEntity.getSize())
                           .navigation();*//*
               }
           });*/
        }

}
