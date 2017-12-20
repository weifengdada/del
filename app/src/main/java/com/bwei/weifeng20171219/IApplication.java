package com.bwei.weifeng20171219;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by acer on 2017/12/19.
 */

public class IApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
