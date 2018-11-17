package com.example.zzh.aiuiriddle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class BaseAcitivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(View view) {
        setFullScreen();
        super.setContentView(view);

    }

    @Override
    public void setContentView(int layoutResID) {
        setFullScreen();
        super.setContentView(layoutResID);


    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setFullScreen();
        super.setContentView(view, params);

    }
    /**
     * 在setContentView 前调用
     */
    protected void setFullScreen(){
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
