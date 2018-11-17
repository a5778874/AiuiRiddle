package com.example.zzh.aiuiriddle.aiui;

import android.os.Bundle;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

public abstract class SpeakHandler implements SynthesizerListener {

    public abstract void onSpeakBegin();
    public abstract void onCompleted(SpeechError var1);;

    @Override
    public void onSpeakProgress(int i, int i1, int i2) {

    }

    @Override
    public void onBufferProgress(int i, int i1, int i2, String s) {

    }

    @Override
    public void onSpeakPaused() {

    }

    @Override
    public void onSpeakResumed() {

    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }
}
