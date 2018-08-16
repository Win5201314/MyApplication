package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class LaunchTelegramActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton launch;
    private AppCompatButton exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        launch = findViewById(R.id.launch);
        exit = findViewById(R.id.exit);
        launch.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.launch: {
                ToastUtil.normalShow(LaunchTelegramActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("LaunchAndExit");
                intent.putExtra("type", 0);
                //发送广播
                sendBroadcast(intent);
                break;
            }
            case R.id.exit: {
                ToastUtil.normalShow(LaunchTelegramActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("LaunchAndExit");
                intent.putExtra("type", 1);
                //发送广播
                sendBroadcast(intent);
                break;
            }
        }
    }
}
