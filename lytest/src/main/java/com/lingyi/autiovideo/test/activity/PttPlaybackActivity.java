package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.entity.PttHistoryEntity;
import com.bnc.activity.service.module.data.PttAudioHistoryDataDao;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.adapter.PttPlaybackAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author  : devyk on 2020-02-24 18:34
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PttPlaybackActivity
 * </pre>
 */
public class PttPlaybackActivity extends Activity {


    @BindView(R.id.tv_metting)
    TextView tvMetting;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.edit_btn)
    LinearLayout edit_btn;

    ArrayList<PttHistoryEntity> lists = new ArrayList<>();

    private PttPlaybackAdapter adapter;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptt_palyback);
        bind = ButterKnife.bind(this);
        edit_btn.setVisibility(View.GONE);
        tvMetting.setVisibility(View.GONE);
        toolbarTitle.setText("对讲语音回放");
        toolbarBack.setVisibility(View.VISIBLE);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        adapter = new PttPlaybackAdapter(lists);
        recyclerview.setAdapter(adapter);
        getPlaybackData();
        pttPlaybackDataListener();

    }

    private void pttPlaybackDataListener() {
        T01Helper.getInstance().getPttEngine().addPttPlaybackDataListener(new PttAudioHistoryDataDao.IPttPlaybackDataChange() {
            @Override
            public void onChange(PttHistoryEntity pttHistoryEntity) {
                String name = Thread.currentThread().getName();
                lists.add(0,pttHistoryEntity);
                adapter.notifyDataSetChanged();
                ToastUtils.showShort("有新的回放数据，请注意查看!");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    private void getPlaybackData() {
        T01Helper.getInstance().getPttEngine().getAllPttAudioHistoryAsyn(new PttAudioHistoryDataDao.IFindCallback() {
            @Override
            public void getPttHistoryData(List<PttHistoryEntity> list) {
                lists.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
