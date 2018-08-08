package com.example.firstxp;

import android.app.Activity;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.util.ToastUtil;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class FooxMain implements IXposedHookLoadPackage {

    private static final String TAG = FooxMain.class.getSimpleName();

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        String packageName = lpparam.packageName;
        Log.i(TAG, "inject into process: " + lpparam.processName + ", package: " + lpparam.packageName);
        XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //获取当前的上下文参数
                Application context = AndroidAppHelper.currentApplication();
                String classname = param.thisObject.getClass().getName();
                String text = lpparam.packageName + "\n" + classname;
                Log.i(TAG, text);
                ToastUtil.normalShow(context, text, true);
            }
        });
        //只有微信才能进去
        if (packageName.equals("com.tencent.mm")) {
            //hook月球界面点击登录按钮事件
            XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Application context = AndroidAppHelper.currentApplication();
                    ToastUtil.normalShow(context, "进来了", true);
                }
            });
            XposedHelpers.findAndHookMethod("com.tencent.mm.ui.account.WelcomeSelectView$2", lpparam.classLoader, "onClick", View.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Application context = AndroidAppHelper.currentApplication();
                    ToastUtil.normalShow(context, "进来了", true);
                }
            });
        }
        /*
        //只有telegram才能进去
        if (packageName.equals("org.telegramkr.messenger")) {
            XposedHelpers.findAndHookMethod("org.telegram.ui.LaunchActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Application context = AndroidAppHelper.currentApplication();
                    ToastUtil.normalShow(context, "进来了", true);
                    Class c = Class.forName("org.telegram.ui.IntroActivity");
                    Intent intent = new Intent(context, c);
                    context.startActivity(intent);
                }
            });
            XposedHelpers.findAndHookMethod("org.telegram.ui.IntroActivity", lpparam.classLoader, "onCreate", View.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                }
            });
        }*/
    }

}
