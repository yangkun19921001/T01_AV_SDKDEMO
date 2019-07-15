package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;

import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.widget.popup.MyCallLayout;

/**
 * Created by yangk on 2018/12/10.
 */

public class TestCallActivity extends Activity {

    private MyCallLayout mCallLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_test_call_activity);
        mCallLayout = findViewById(R.id.myl);
        mCallLayout.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCallLayout.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCallLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCallLayout.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCallLayout.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallLayout.onDestroy();
    }
}
