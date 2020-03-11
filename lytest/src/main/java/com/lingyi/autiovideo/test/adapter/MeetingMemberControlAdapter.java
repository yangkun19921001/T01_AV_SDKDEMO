package com.lingyi.autiovideo.test.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.model.VoipContactEntity;

import java.util.List;

/**
 * Created by yangk on 2017/12/22.
 */

public class MeetingMemberControlAdapter extends BaseQuickAdapter<VoipContactEntity, BaseViewHolder> {
    private IMeetingControlListener iMeetingControlListener;


    public MeetingMemberControlAdapter(@Nullable List<VoipContactEntity> data) {
        super(R.layout.adapter_metting_detail, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final VoipContactEntity voipContactEntity) {
        String name = voipContactEntity.getName();
        boolean isShowBtn = false;
        if (!voipContactEntity.isTalk() && voipContactEntity.isJoin()) {
            name = (voipContactEntity.getName() != null
                    && voipContactEntity.getName() == null) ? voipContactEntity.getNumber() : name;
            isShowBtn = true;
        } else if (voipContactEntity.isTalk()) {
            name = (voipContactEntity.getName() != null
                    && voipContactEntity.getName() == null) ? voipContactEntity.getNumber() : name;
            name = name + ":[正在说话...]";
            isShowBtn = true;
        } else if (!voipContactEntity.isJoin()) {
            name = (voipContactEntity.getName() != null
                    && voipContactEntity.getName() == null) ? voipContactEntity.getNumber() : name;
            name = "邀请中..." + name;
            isShowBtn = false;
        }

        baseViewHolder.setText(R.id.tv_metting_name, (voipContactEntity.getName() != null
                && voipContactEntity.getName() == null) ? voipContactEntity.getNumber() : name)
                .setVisible(R.id.btn_del, isShowBtn)
                .setVisible(R.id.btn_mute, isShowBtn)
                .setOnClickListener(R.id.btn_mute, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iMeetingControlListener != null)
                            iMeetingControlListener.onMute(baseViewHolder.getAdapterPosition(), voipContactEntity);

                    }
                }).setOnClickListener(R.id.btn_del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iMeetingControlListener != null)
                    iMeetingControlListener.onDel(baseViewHolder.getAdapterPosition(), voipContactEntity);
            }
        });
        Button button = baseViewHolder.getView(R.id.btn_mute);
        button.setText(voipContactEntity.isSelect() == true ? "取消禁言" : "禁言");
    }


    public void addChildClickListener(IMeetingControlListener iMeetingControlListener) {
        this.iMeetingControlListener = iMeetingControlListener;
    }


    public interface IMeetingControlListener {

        void onMute(int adapterPosition, VoipContactEntity voipContactEntity);

        void onDel(int adapterPosition, VoipContactEntity voipContactEntity);
    }
}
