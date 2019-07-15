package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bnc.activity.T01Helper;
import com.lingyi.autiovideo.test.R;

public class CallInOrOutActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_in
        );

        findViewById(R.id.acceptCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T01Helper.getInstance().getCallEngine().acceptCall();
            }
        });

        findViewById(R.id.hangUpCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T01Helper.getInstance().getCallEngine().hangUpCall();
            }
        });
    }
}
