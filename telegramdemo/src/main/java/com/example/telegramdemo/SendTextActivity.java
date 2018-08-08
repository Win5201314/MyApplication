package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class SendTextActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText groupLink;
    private AppCompatEditText text;
    private AppCompatButton send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendtext);
        groupLink = findViewById(R.id.groupLink);
        text = findViewById(R.id.text);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            ToastUtil.normalShow(this, "已经发送广播!", true);
            Intent intent = new Intent();
            intent.setAction("sendText");
            String link = groupLink.getText().toString();
            intent.putExtra("link", link.trim());
            //intent.putExtra("link", "hhhhhh696969");
            //发送的文字
            intent.putExtra("text", text.getText().toString());
            //发送广播
            sendBroadcast(intent);
        }
    }

}
