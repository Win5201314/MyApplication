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

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText firstName;
    private AppCompatEditText lastName;
    private AppCompatEditText phone;
    private AppCompatButton addContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addContact) {
            ToastUtil.normalShow(this, "已经发送广播!", true);
            Intent intent = new Intent();
            intent.setAction("addContact");
            String f = firstName.getText().toString();
            if (TextUtils.isEmpty(f)) return;
            intent.putExtra("firstName", f.trim());
            String la = lastName.getText().toString();
            if (TextUtils.isEmpty(la)) return;
            intent.putExtra("lastName", la.trim());
            intent.putExtra("code", "86");
            String p = phone.getText().toString();
            if (TextUtils.isEmpty(p)) return;
            intent.putExtra("phoneNumber", p.trim());
            //发送广播
            sendBroadcast(intent);
        }
    }
}
