package com.example.telegramdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class GroupCountActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText groupLink;
    private AppCompatButton send;
    private AppCompatTextView number;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    number.setText(String.valueOf("该群当前群人数为: " + result));
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
        setContentView(R.layout.groupcount);
        groupLink = findViewById(R.id.groupLink);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
        number = findViewById(R.id.number);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            ToastUtil.normalShow(this, "已经发送广播!", true);
            Intent intent = new Intent();
            intent.setAction("groupCount");
            String link = groupLink.getText().toString();
            intent.putExtra("link", link.trim());
            //intent.putExtra("link", "hhhhhh696969");
            //发送广播
            sendBroadcast(intent);
            handler.sendEmptyMessageDelayed(1, 5000);
        }
    }

    private String result = "";
    public void readInfo(){
        File file = new File("/sdcard/groupcounts.txt");
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            result = br.readLine();
            ToastUtil.normalShow(GroupCountActivity.this, result, true);
            handler.sendEmptyMessage(0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ToastUtil.normalShow(GroupCountActivity.this, "读取文件失败", true);
        }
    }

}
