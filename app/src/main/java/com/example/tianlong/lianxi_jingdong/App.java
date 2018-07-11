package com.example.tianlong.lianxi_jingdong;

import android.app.Application;
import android.content.Context;

/**
 * 提供一个上下文的环境，让前面锁住的那个类可以在整个项目中运行
 *
 *
 */
public class App extends Application {


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
