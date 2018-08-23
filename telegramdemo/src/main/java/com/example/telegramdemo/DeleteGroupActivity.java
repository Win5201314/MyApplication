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

public class DeleteGroupActivity extends AppCompatActivity {

    private AppCompatEditText groupLink;
    private AppCompatButton deleteGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);
        groupLink = findViewById(R.id.groupLink);
        deleteGroup = findViewById(R.id.deleteGroup);
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = groupLink.getText().toString();
                if (TextUtils.isEmpty(link)) return;
                ToastUtil.normalShow(DeleteGroupActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("deleteGroup");
                intent.putExtra("link", link.trim());
                //发送广播
                sendBroadcast(intent);
            }
        });
    }

}
