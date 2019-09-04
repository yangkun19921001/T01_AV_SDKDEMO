package com.lingyi.autiovideo.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangk on 2018/1/5.
 */

public abstract class BaseFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    private int layout;
    Unbinder unbinder;
    public View mView;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }

    public View initView(LayoutInflater inflater, ViewGroup var2, Bundle var3) {
        mView = inflater.inflate(getLayout(), var2, false);
        return mView;
    }

    protected abstract void initData();

    protected abstract void initListener();


    public abstract int getLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public static void configRecycleView(RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
