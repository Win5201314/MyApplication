package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class LoginOutActivity extends AppCompatActivity {

    private AppCompatButton loginout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginout);
        loginout = findViewById(R.id.loginout);
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.normalShow(LoginOutActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("loginout");
                //发送广播
                sendBroadcast(intent);
            }
        });
    }
}
