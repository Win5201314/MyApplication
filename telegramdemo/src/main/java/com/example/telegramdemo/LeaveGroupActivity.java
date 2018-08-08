package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class LeaveGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText leaveGroupLink;
    private AppCompatButton leaveGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave);
        leaveGroupLink = findViewById(R.id.leaveGroupLink);
        leaveGroup = findViewById(R.id.leaveGroup);
        leaveGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.leaveGroup) {
            ToastUtil.normalShow(this, "已经发送广播!", true);
            Intent intent = new Intent();
            intent.setAction("leaveGroup");
            String leavelink = leaveGroupLink.getText().toString();
            intent.putExtra("link", leavelink.trim());
            //intent.putExtra("link", "hhhhhh696969");
            //发送广播
            sendBroadcast(intent);
        }
    }
}
