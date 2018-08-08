package com.example.asdemo;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AccessibilityServiceTest extends AccessibilityService {

    private final static int ACTION_CLICK = AccessibilityNodeInfo.ACTION_CLICK;

    //可在此方法中配置服务的相关配置，代替配置文件的方式
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("XLZH:", "OnServiceConnected");
    }

    //监听事件类型，根据事件类型不同做不同的处理。事件类型较多，各位可以打印出来筛选出来自己要监听的事件类型
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("XLZH:", event.toString());
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        String pageName = event.getClassName().toString();
        if (nodeInfo == null) return;
        //点击开始聊天
        if (pageName.equals("org.telegram.ui.IntroActivity")) {
            delay(1500);
            List<AccessibilityNodeInfo> nexts = nodeInfo.findAccessibilityNodeInfosByViewId("org.telegramkr.messenger:id/start_messaging_button");
            if (nexts != null && nexts.size() > 0) {
                if (nexts.get(0).performAction(ACTION_CLICK)) {
                    delay(1500);
                    //Your phone

                }
            }
        }
        //org.telegram.ui.IntroActivity
        //org.telegram.ui.MainActivity
        //org.telegram.ui.LaunchActivity
    }

    private static AccessibilityNodeInfo flushUI(AccessibilityService as) {
        for (int i = 0; i < 15; i++) {// 最长延时15s
            AccessibilityNodeInfo nodeInfo = as.getRootInActiveWindow();
            if (nodeInfo != null) {
                return nodeInfo;
            }
            delay(1000);
        }
        return null;
    }

    private static void delay(int xml) {
        if (xml <= 0) return;
        for (int i = 0; i < (xml / 100); i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
