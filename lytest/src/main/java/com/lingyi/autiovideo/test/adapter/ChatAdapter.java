package com.lingyi.autiovideo.test.adapter;

import android.support.annotation.Nullable;

import com.bnc.activity.entity.MsgMessageEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyi.autiovideo.test.R;

import java.util.List;

/**
 * <pre>
 *     author  : devyk on 2019-09-04 15:43
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ChatAdapter
 * </pre>
 */
public class ChatAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ChatAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_content, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_message,item);

    }
}
