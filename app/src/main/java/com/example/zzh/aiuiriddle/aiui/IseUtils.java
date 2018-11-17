package com.example.zzh.aiuiriddle.aiui;

import android.content.Context;
import android.util.Log;

import com.example.zzh.aiuiriddle.MainActivity;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechEvaluator;

public class IseUtils {

    private static IseUtils instance;
    private Context context;
    private SpeechEvaluator mIse;

    public static synchronized IseUtils getInstance(Context context) {
        if (instance == null) {
            instance = new IseUtils(context);
        }
        return instance;
    }

    private IseUtils(Context context) {
        this.context = context;
        mIse = SpeechEvaluator.createEvaluator(context, null);
        initIse();
    }

    private void initIse() {
        Log.d("TAG", "initIse: " + mIse);
        mIse.setParameter(SpeechConstant.LANGUAGE, "zh_cn");  //评测语种：中文
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, "read_sentence");  //评测类型:句子
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, "10000");// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIse.setParameter(SpeechConstant.VAD_EOS, "4000");// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");//录音超时，当录音达到时限将自动触发.默认-1（无超时）
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, "complete");
    }

    //开始评测
    public void startIseEvaluating(String text, EvaluatorListener evaluatorListener) {
        if (mIse != null)
            mIse.startEvaluating(text, null, evaluatorListener);
    }

    //是否正在评测
    public boolean isIseEvaluating() {
        if (mIse == null) return false;
        return mIse.isEvaluating();
    }

    //取消评测
    public void cancelEvaluating() {
        if (isIseEvaluating())
            mIse.cancel();
    }

}
