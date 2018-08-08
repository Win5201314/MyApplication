package com.example.firstxp;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.util.ToastUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton zhuce;
    private AppCompatButton send;
    private AppCompatEditText sms;
    private AppCompatButton sendSMS;
    private AppCompatButton addContact;
    private AppCompatButton joinGroup;
    private AppCompatEditText groupLink;
    private AppCompatEditText leaveGroupLink;
    private AppCompatButton leaveGroup;
    private AppCompatEditText addFriendsGroupLink;
    private AppCompatButton addFriendsGroup;
    private AppCompatEditText groupNumber;
    private AppCompatButton groupNumberBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zhuce = findViewById(R.id.zhuce);
        zhuce.setOnClickListener(this);
        send = findViewById(R.id.send);
        sms = findViewById(R.id.sms);
        sendSMS = findViewById(R.id.sendSMS);
        sendSMS.setOnClickListener(this);
        send.setOnClickListener(this);
        addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(this);
        joinGroup = findViewById(R.id.joinGroup);
        joinGroup.setOnClickListener(this);
        groupLink = findViewById(R.id.groupLink);
        leaveGroupLink = findViewById(R.id.leaveGroupLink);
        leaveGroup = findViewById(R.id.leaveGroup);
        leaveGroup.setOnClickListener(this);
        addFriendsGroupLink = findViewById(R.id.addFriendsGroupLink);
        addFriendsGroup = findViewById(R.id.addFriendsGroup);
        addFriendsGroup.setOnClickListener(this);
        groupNumber = findViewById(R.id.groupNumber);
        groupNumberBtn = findViewById(R.id.groupNumberBtn);
        groupNumberBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.zhuce:
                ToastUtil.normalShow(this, "已经发送广播!", true);
                intent.setAction("zhuce");
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.send:
                ToastUtil.normalShow(this, "已经发送广播!", true);
                //对应BroadcastReceiver中intentFilter的action
                intent.setAction("sendPhoneNumber");
                intent.putExtra("code", "+86");
                intent.putExtra("phoneNumber", "13480901446");
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.sendSMS:
                ToastUtil.normalShow(this, "已经发送广播!", true);
                //对应BroadcastReceiver中intentFilter的action
                intent.setAction("sendSMS");
                String code = sms.getText().toString();
                intent.putExtra("code", code.trim());
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.addContact:
                ToastUtil.normalShow(this, "已经发送广播!", true);
                //对应BroadcastReceiver中intentFilter的action
                intent.setAction("addContact");
                intent.putExtra("firstName", "sa");
                intent.putExtra("lastName", "ds");
                intent.putExtra("code", "86");
                intent.putExtra("phoneNumber", "18718519504");
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.joinGroup:
                ToastUtil.normalShow(this, "已经发送广播!", true);
                //对应BroadcastReceiver中intentFilter的action
                intent.setAction("joinGroup");
                String link = groupLink.getText().toString();
                intent.putExtra("link", link.trim());
                //intent.putExtra("link", "hhhhhh696969");
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.leaveGroup:
                ToastUtil.normalShow(this, "已经发送广播!", true);
                //对应BroadcastReceiver中intentFilter的action
                intent.setAction("leaveGroup");
                String leavelink = leaveGroupLink.getText().toString();
                intent.putExtra("link", leavelink.trim());
                //intent.putExtra("link", "hhhhhh696969");
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.addFriendsGroup:
                ToastUtil.normalShow(this, "已经发送广播!", true);
                //对应BroadcastReceiver中intentFilter的action
                intent.setAction("addFriendsGroup");
                String addlink = addFriendsGroupLink.getText().toString();
                intent.putExtra("link", addlink);
                //intent.putExtra("link", "hhhhhh696969");
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.groupNumberBtn: {
                ToastUtil.normalShow(this, "已经发送广播!", true);
                /*//对应BroadcastReceiver中intentFilter的action
                intent.setAction("groupNumber");
                String linkNumber = groupNumber.getText().toString();
                //intent.putExtra("link", linkNumber.trim());
                intent.putExtra("link", "hhhhhh696969");
                //intent.putExtra("link", "AA");
                //发送广播
                sendBroadcast(intent);*/
                //startAppByPackageName(getApplicationContext(),"org.telegram.ui");
                //doStartApplicationWithPackageName("org.telegram.messenger");
                try {
                    Intent intent1 = new Intent(Intent.ACTION_MAIN);
                    intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent1.addCategory("android.intent.category.MULTIWINDOW_LAUNCHER");
                    // 设置ComponentName参数1:packagename参数2:MainActivity路径
                    ComponentName cn = new ComponentName("org.telegram.messenger", "org.telegram.ui.LaunchActivity");

                    intent.setComponent(cn);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Log.d("TAG", e.getMessage());
                }

                break;
            }
        }
    }

    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    public static boolean startAppByPackageName(Context context, String packageName) {
        return startAppByPackageName(context, packageName, (Map)null);
    }

    public static boolean startAppByPackageName(Context context, String packageName, Map<String, String> param) {
        PackageInfo pi = null;

        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent("android.intent.action.MAIN", (Uri)null);
            resolveIntent.addCategory("android.intent.category.LAUNCHER");
            if (Build.VERSION.SDK_INT >= 4) {
                resolveIntent.setPackage(pi.packageName);
            }

            List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = (ResolveInfo)apps.iterator().next();
            if (ri != null) {
                String packageName1 = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setFlags(268435456);
                intent.addCategory("android.intent.category.LAUNCHER");
                ComponentName cn = new ComponentName(packageName1, className);
                intent.setComponent(cn);
                if (param != null) {
                    Iterator var11 = param.entrySet().iterator();

                    while(var11.hasNext()) {
                        Map.Entry<String, String> en = (Map.Entry)var11.next();
                        intent.putExtra((String)en.getKey(), (String)en.getValue());
                    }
                }

                context.startActivity(intent);
                return true;
            }
        } catch (Exception var13) {
            var13.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "启动失败", 1).show();
        }

        return false;
    }

}
