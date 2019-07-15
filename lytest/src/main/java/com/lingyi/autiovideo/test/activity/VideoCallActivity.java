package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.bnc.activity.T01Helper;
import com.lingyi.autiovideo.test.R;

public class VideoCallActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R
                .layout.video_call_in_call
        );

        FrameLayout remote = findViewById(R.id.video_call_remote_video);
        FrameLayout local = findViewById(R.id.video_call_local_video);


        T01Helper.getInstance().getCallEngine().startPreviewLocalVideo(local, false
        );
        T01Helper.getInstance().getCallEngine().startPreviewRemoteVideo(remote
        );
    }


    @Override
    public void onStart() {
        super.onStart();
        T01Helper.getInstance().getCallEngine().onCallStart();
    }

    public void onResume() {
        super.onResume();
        T01Helper.getInstance().getCallEngine().onResume();
    }

    public void onPause() {
        super.onPause();
        T01Helper.getInstance().getCallEngine().onCallPause();
    }

    public void onStop() {
        super.onStop();
        T01Helper.getInstance().getCallEngine().onCallStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        T01Helper.getInstance().getCallEngine().onCallDestroy();
        T01Helper.getInstance().getCallEngine().stopLocalVideo();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        T01Helper.getInstance().getCallEngine().hangUpCall();
        finish();
    }
}
