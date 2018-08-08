package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText phone;
    private AppCompatButton send;
    private AppCompatEditText sms;
    private AppCompatButton sendSMS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        phone = findViewById(R.id.phone);
        send = findViewById(R.id.send);
        sms = findViewById(R.id.sms);
        sendSMS = findViewById(R.id.sendSMS);

        send.setOnClickListener(this);
        sendSMS.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send: {
                ToastUtil.normalShow(this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("sendPhoneNumber");
                intent.putExtra("code", "+86");
                String phoneNumber = phone.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) return;
                intent.putExtra("phoneNumber", phoneNumber.trim());
                //发送广播
                sendBroadcast(intent);
                break;
            }
            case R.id.sendSMS: {
                ToastUtil.normalShow(this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("sendSMS");
                String code = sms.getText().toString();
                intent.putExtra("code", code.trim());
                //发送广播
                sendBroadcast(intent);
                break;
            }
        }
    }
}
