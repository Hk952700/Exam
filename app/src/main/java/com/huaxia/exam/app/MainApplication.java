package com.huaxia.exam.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.huaxia.exam.service.ExamMainSrevice;
import com.huaxia.exam.utils.CrashHandler;
import com.huaxia.exam.utils.SharedPreUtils;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化异常捕获
        CrashHandler.getInstance().init(getApplicationContext());
        //将答题数量初始化为0
       // SharedPreUtils.put(getApplicationContext(), "answer_num", 0);
        //初始化Fresco
        Fresco.initialize(getApplicationContext());
    }


}
