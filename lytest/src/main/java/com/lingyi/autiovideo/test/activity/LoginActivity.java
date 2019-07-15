package com.lingyi.autiovideo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.callback.IIsLoginListener;
import com.bnc.activity.callback.LoginCallBack;
import com.lingyi.autiovideo.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yangk on 2018/1/4.
 */

public class LoginActivity extends AppCompatActivity  {
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

    /**
     * 初始化消息转发器
     * <p>
     * loginState == 1,在线；2，离线
     */
    private int loginState; //是否能登陆

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind = ButterKnife.bind(this);
        String trim = etUser.getText().toString().trim();
        pro.setVisibility(View.VISIBLE);
        initListener();
    }


    private void initListener() {

        T01Helper.getInstance().getRegisterEngine().getIsRegister(new IIsLoginListener() {
            @Override
            public void onLoginState(int i) {
                if (i == 1){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                loginState = i;
                pro.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        if (loginState == 2) {
            if (TextUtils.isEmpty(etUser.getText().toString().trim()) ||
                    TextUtils.isEmpty(pwd.getText().toString().trim()) ||
                    TextUtils.isEmpty(port.getText().toString().trim()) ||
                    TextUtils.isEmpty(ip.getText().toString().trim())
                    ) {
                ToastUtils.showLong("检查用户名或密码是否填写？");
            } else {
                onLogin(etUser.getText().toString().trim(), pwd.getText().toString().trim(),ip.getText().toString().trim(),port.getText().toString().trim());
            }
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }




    public void onLogin(String userId, String pwd, String server_ip, String server_port){
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
}
