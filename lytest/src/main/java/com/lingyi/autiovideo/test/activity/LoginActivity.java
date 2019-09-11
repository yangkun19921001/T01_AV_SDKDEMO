package com.lingyi.autiovideo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.callback.IIsLoginListener;
import com.bnc.activity.callback.LoginCallBack;
import com.bnc.activity.utils.LogHelper;
import com.lingyi.autiovideo.test.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yangk on 2018/1/4.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.ip)
    EditText ip;
    @BindView(R.id.port)
    EditText port;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.pro)
    ProgressBar pro;
    private Unbinder bind;


    /**
     *
     *  String APP_DOMAIN = "http://39.106.172.189:29014";
     //    String APP_DOMAIN = "http://192.168.60.27:29014";
     String host = "/androidApi/getPermissions.action";

     int httpPort = 29014;

     int TCP_PORT = 29005;

     int UDP_PORT = 2801;
     *
     *
     */




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind = ButterKnife.bind(this);
        String trim = etUser.getText().toString().trim();
        pro.setVisibility(View.VISIBLE);
        initListener();

        OkGo.getInstance().init(getApplication());
        appRequest();
    }


    private void initListener() {
        pro.setVisibility(View.GONE);
        if (T01Helper.getInstance().getRegisterEngine().isRegister()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
            if (TextUtils.isEmpty(etUser.getText().toString().trim()) ||
                    TextUtils.isEmpty(pwd.getText().toString().trim()) ||
                    TextUtils.isEmpty(port.getText().toString().trim()) ||
                    TextUtils.isEmpty(ip.getText().toString().trim())
            ) {
                ToastUtils.showLong("检查用户名或密码是否填写？");
            } else {
                onLogin(etUser.getText().toString().trim(), pwd.getText().toString().trim(), ip.getText().toString().trim(), port.getText().toString().trim());
            }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    public void onLogin(String userId, String pwd, String server_ip, String server_port) {
        T01Helper.getInstance().getRegisterEngine().login(userId, pwd, server_ip, server_port, new LoginCallBack() {
            @Override
            public void onLoginSucceed() {
                pro.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                // 关闭登录界面
                finish();
            }

            @Override
            public void onLoginError(String reason) {
                ToastUtils.showShort(reason);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onLogin() {
                pro.setVisibility(View.VISIBLE);
            }
        });
    }


    public void appRequest() {


        OkGo.<File>get("http://47.105.211.246:8080/rstoneCmd/upload/ver/dc_v2.6.3_release.apk").execute(new com.lzy.okgo.callback.FileCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                // 安装软件
                LogHelper.i("APP-onSuccess-", "onSuccess");

            }

            @Override
            public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                LogHelper.i("APP-onStart-", "start");
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                LogHelper.i("APP-onError-", "异常" + response.getException().getMessage());

            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                String downloadLength = Formatter.formatFileSize(getApplicationContext(), progress.currentSize);
                String totalLength = Formatter.formatFileSize(getApplicationContext(), progress.totalSize);
//              tvDownloadSize.setText(downloadLength + "/" + totalLength);
                String netSpeed = Formatter.formatFileSize(getApplicationContext(), progress.speed);
                String pr = (Math.round(progress.currentSize * 10000) * 1.0f / 100) + "%";
                int pro = (int) (Math.round(progress.currentSize * 10000) * 1.0f / progress.totalSize);
                LogHelper.i("APP-downloadProgress-", pro + "");
            }
        });
    }
}
