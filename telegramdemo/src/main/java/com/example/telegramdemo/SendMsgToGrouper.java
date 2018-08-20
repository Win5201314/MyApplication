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

public class SendMsgToGrouper extends AppCompatActivity {

    private AppCompatEditText groupLink;
    private AppCompatEditText msg;
    private AppCompatButton send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmsggrouper);
        groupLink = findViewById(R.id.groupLink);
        msg = findViewById(R.id.msg);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.normalShow(SendMsgToGrouper.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("grouperMsg");
                String link = groupLink.getText().toString();
                if (TextUtils.isEmpty(link)) return;
                intent.putExtra("link", link.trim());

                String m = msg.getText().toString();
                if (TextUtils.isEmpty(m)) return;
                intent.putExtra("msg", m.trim());
                //发送广播
                sendBroadcast(intent);
            }
        });
    }
}
