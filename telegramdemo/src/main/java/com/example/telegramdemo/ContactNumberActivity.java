package com.example.telegramdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ContactNumberActivity extends AppCompatActivity {

    private AppCompatButton btn;
    private AppCompatTextView number;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    number.setText("您当前通讯录好友人数: " + result);
                    break;
                case 1:
                    //读取文件
                    readInfo();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_number);
        number = findViewById(R.id.number);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.normalShow(ContactNumberActivity.this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("contactNumber");
                //发送广播
                sendBroadcast(intent);
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        });
    }
    private String result = "";
    public void readInfo(){
        File file = new File("/sdcard/contacts.txt");
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            result = br.readLine();
            ToastUtil.normalShow(ContactNumberActivity.this, result, true);
            handler.sendEmptyMessage(0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ToastUtil.normalShow(ContactNumberActivity.this, "读取文件失败", true);
        }
    }

}
