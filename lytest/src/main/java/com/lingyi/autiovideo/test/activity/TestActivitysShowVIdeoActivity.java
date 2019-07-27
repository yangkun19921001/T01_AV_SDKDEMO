package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.bnc.activity.T01Helper;
import com.lingyi.autiovideo.test.Constants;
import com.lingyi.autiovideo.test.R;

import org.doubango.ngn.sip.NgnAVSession;


public class TestActivitysShowVIdeoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_show_activty);
        FrameLayout frameLayout = findViewById(R.id.fl);
        long longExtra = getIntent().getLongExtra(Constants.SESSION_ID, -1);
        NgnAVSession ngnAVSession = Constants.mSessionMap.get(longExtra);
        T01Helper.getInstance().getCallEngine().startPreviewRemoteVideo(ngnAVSession, frameLayout);
    }
}
