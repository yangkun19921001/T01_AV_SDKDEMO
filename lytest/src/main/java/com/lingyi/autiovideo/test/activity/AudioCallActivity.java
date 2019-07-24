package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bnc.activity.T01Helper;
import com.lingyi.autiovideo.test.Constants;
import com.lingyi.autiovideo.test.R;

public class AudioCallActivity extends Activity {

    private boolean isHandfree = false;
    private boolean isMute = false;
    private AudioCallReceiver mCllReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        findViewById(R.id.handfree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHandfree = !isHandfree;
                T01Helper.getInstance().getCallEngine().isHandsfree(isHandfree);

            }
        });


        findViewById(R.id.mute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMute = !isMute;
                T01Helper.getInstance().getCallEngine().isMute(isMute);
            }
        });
;
        mCllReceiver = new AudioCallReceiver();
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCllReceiver != null) {
            unregisterReceiver(mCllReceiver);
            mCllReceiver = null;
        }

    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.CALL_ACION);
        intentFilter.addAction(Constants.CALL_IN_OR_OUR_ACION);
        registerReceiver(mCllReceiver, intentFilter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        T01Helper.getInstance().getCallEngine().hangUpCall();
        finish();
    }

    private class AudioCallReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent .getAction().equals(Constants.CALL_ACION)){
                T01Helper.getInstance().getCallEngine().hangUpCall();
                finish();
            }
        }
    }
}
