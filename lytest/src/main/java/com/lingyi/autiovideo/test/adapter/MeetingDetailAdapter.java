package com.lingyi.autiovideo.test.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.model.VoipContactEntity;

import java.util.List;

/**
 * Created by yangk on 2017/12/22.
 */

public class MeetingDetailAdapter extends BaseQuickAdapter<VoipContactEntity,BaseViewHolder> {
    public MeetingDetailAdapter(@Nullable List<VoipContactEntity> data) {
        super(R.layout.adapter_metting_detail,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, VoipContactEntity voipContactEntity) {
        String name = voipContactEntity.getName();
       baseViewHolder.setText(R.id.tv_metting_name, (voipContactEntity.getName() != null
                && voipContactEntity.getName().equals("null")) ? voipContactEntity.getNumber() : name);
    }
}
