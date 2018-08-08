package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class CreatGroupActivity extends AppCompatActivity {

    private AppCompatButton create;
    private AppCompatEditText groupName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroup);
        create = findViewById(R.id.creat);
        groupName = findViewById(R.id.groupName);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.normalShow(CreatGroupActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("createGroup");
                String name = groupName.getText().toString();
                intent.putExtra("groupName", name.trim());
                //发送广播
                sendBroadcast(intent);
            }
        });
    }
}
