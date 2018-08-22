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

public class SendMsgTocontactActivity extends AppCompatActivity {

    private AppCompatButton send;
    private AppCompatEditText msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmsgtocontact);
        send = findViewById(R.id.send);
        msg = findViewById(R.id.msg);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = msg.getText().toString();
                if (TextUtils.isEmpty(a)) return;
                ToastUtil.normalShow(SendMsgTocontactActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("sendMsgToContact");
                intent.putExtra("msg", a.trim());
                //发送广播
                sendBroadcast(intent);
            }
        });
    }
}
