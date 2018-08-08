package com.example.telegramdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class AddContactJoinGroup extends AppCompatActivity {

    private AppCompatEditText groupName;
    private AppCompatEditText number;
    private AppCompatButton add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_join_group);
        groupName = findViewById(R.id.groupName);
        number = findViewById(R.id.number);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.normalShow(AddContactJoinGroup.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("addContactJoinGroup");
                String name = groupName.getText().toString();
                String count = number.getText().toString();
                intent.putExtra("groupName", name);
                intent.putExtra("number", count);
                //发送广播
                sendBroadcast(intent);
            }
        });
    }
}
