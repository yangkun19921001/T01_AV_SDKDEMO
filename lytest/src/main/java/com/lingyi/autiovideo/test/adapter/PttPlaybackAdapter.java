package com.lingyi.autiovideo.test.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.entity.PttHistoryEntity;
import com.bnc.activity.service.module.play.AudioTracker;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyi.autiovideo.test.R;

import org.doubango.ngn.Constants;

import java.util.List;

/**
 * <pre>
 *     author  : devyk on 2020-02-24 18:48
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PttPlaybackAdapter
 * </pre>
 */
public class PttPlaybackAdapter extends BaseQuickAdapter<PttHistoryEntity, BaseViewHolder> {
    boolean isPlay = false;

    public PttPlaybackAdapter(@Nullable List<PttHistoryEntity> data) {
        super(R.layout.adapter_ptt_audio_playback, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PttHistoryEntity item) {
        helper.setText(R.id.tv_groupName, item.getGroupName())
                .setText(R.id.tv_talkName, item.talkName + getAudioType(item.audioType))
                .setText(R.id.tv_time, item.getRecordAudioTime());

        final Button  play = helper.getView(R.id.btn_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    T01Helper.getInstance().getPttEngine().stopPttPlayback();
                    isPlay = false;
                    update(play);
                    return;
                }


                T01Helper.getInstance().getPttEngine().playPttPlayback(item.getRecordAudioPath(), new AudioTracker.IPlayListener() {
                    @Override
                    public void onStart() {
                        ToastUtils.showShort("开始播放");
                        isPlay = true;
                        update(play);
                    }

                    @Override
                    public void onStop() {
                        ToastUtils.showShort("停止播放");
                        isPlay = false;
                        update(play);
                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showShort("播放错误:" + error);
                        isPlay = false;
                        update(play);
                    }
                });

            }
        });

    }

    private void update(Button play) {
        play.setText(isPlay == true ? "停止播放" : "播放");
    }

    private String getAudioType(int audioType) {
        switch (audioType) {
            case Constants.IMessageNumber.CALL_SINGLE_MSG://单呼
                return "[单呼]";
            case Constants.IMessageNumber.CALL_MULTI_MSG://单呼
                return "[组呼]";
            case Constants.IMessageNumber.CALL_ALL_MSG://单呼
                return "[全呼]";
        }
        return "";
    }
}
