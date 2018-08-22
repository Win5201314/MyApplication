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

public class LaqunActivity extends AppCompatActivity {

    private AppCompatEditText firstLink;
    private AppCompatEditText secondLink;
    private AppCompatButton send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laqun);
        firstLink = findViewById(R.id.firstLink);
        secondLink = findViewById(R.id.secondLink);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = firstLink.getText().toString();
                String b = secondLink.getText().toString();
                if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) return;
                ToastUtil.normalShow(LaqunActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("laqun");
                intent.putExtra("firstLink", a.trim());
                intent.putExtra("secondLink", b.trim());
                //发送广播
                sendBroadcast(intent);
            }
        });
    }
}
