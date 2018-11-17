package com.example.zzh.aiuiriddle;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

public class Myapplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
    }
}
