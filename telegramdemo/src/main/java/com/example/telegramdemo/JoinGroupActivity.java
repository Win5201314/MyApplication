package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class JoinGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton joinGroup;
    private AppCompatEditText groupLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        joinGroup = findViewById(R.id.joinGroup);
        joinGroup.setOnClickListener(this);
        groupLink = findViewById(R.id.groupLink);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.joinGroup) {
            ToastUtil.normalShow(this, "已经发送广播!", true);
            Intent intent = new Intent();
            intent.setAction("joinGroup");
            String link = groupLink.getText().toString();
            intent.putExtra("link", link.trim());
            //intent.putExtra("link", "hhhhhh696969");
            //发送广播
            sendBroadcast(intent);
        }
    }
}
