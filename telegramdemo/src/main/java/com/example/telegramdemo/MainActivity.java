package com.example.telegramdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton zhuce;
    private AppCompatButton login;
    private AppCompatButton addContact;
    private AppCompatButton joinGroup;
    private AppCompatButton leaveGroup;
    private AppCompatButton creatGroup;
    private AppCompatButton contactNumberCount;
    private AppCompatButton addContactToGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zhuce = findViewById(R.id.zhuce);
        login = findViewById(R.id.login);
        addContact = findViewById(R.id.addContact);
        joinGroup = findViewById(R.id.joinGroup);
        leaveGroup = findViewById(R.id.leaveGroup);
        creatGroup = findViewById(R.id.creatGroup);
        contactNumberCount = findViewById(R.id.contactNumberCount);
        addContactToGroup = findViewById(R.id.addContactToGroup);

        zhuce.setOnClickListener(this);
        login.setOnClickListener(this);
        addContact.setOnClickListener(this);
        joinGroup.setOnClickListener(this);
        leaveGroup.setOnClickListener(this);
        creatGroup.setOnClickListener(this);
        contactNumberCount.setOnClickListener(this);
        addContactToGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhuce: {
                ToastUtil.normalShow(this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("register");
                //发送广播
                sendBroadcast(intent);
                break;
            }
            case R.id.login: {
                startActivity(new Intent(this, LoginActivity.class));
                break;
            }
            case R.id.addContact: {
                startActivity(new Intent(this, AddContactActivity.class));
                break;
            }
            case R.id.joinGroup: {
                startActivity(new Intent(this, JoinGroupActivity.class));
                break;
            }
            case R.id.leaveGroup: {
                startActivity(new Intent(this, LeaveGroupActivity.class));
                break;
            }
            case R.id.creatGroup: {
                startActivity(new Intent(this, CreatGroupActivity.class));
                break;
            }
            case R.id.contactNumberCount: {
                startActivity(new Intent(this, ContactNumberActivity.class));
                break;
            }
            case R.id.addContactToGroup: {
                startActivity(new Intent(this, AddContactJoinGroup.class));
                break;
            }
        }
    }
}
