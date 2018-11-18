package com.example.zzh.aiuiriddle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzh.aiuiriddle.aiui.IseUtils;
import com.example.zzh.aiuiriddle.aiui.TTSUtils;
import com.example.zzh.aiuiriddle.entity.RiddleBean;
import com.example.zzh.aiuiriddle.utils.ToastUtils;
import com.example.zzh.aiuiriddle.xml.Result;
import com.example.zzh.aiuiriddle.xml.XmlResultParser;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechError;

import java.io.Serializable;

public class PagerFragment extends Fragment {

    private RiddleBean riddleBean;
    private TextView tv_tip_text;
    private TextView tv_text;
    private TextView tv_tip;


    public static PagerFragment newInstance(RiddleBean riddleBean) {

        Bundle args = new Bundle();
        args.putSerializable("riddleBean", riddleBean);

        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.pager_riddle, null);
        init(view);
        return view;
    }




    private void init(View root) {
        riddleBean = (RiddleBean) getArguments().getSerializable("riddleBean");
        tv_tip_text = root.findViewById(R.id.tv_tip_text);
        tv_tip = root.findViewById(R.id.tv_tip);
        tv_text = root.findViewById(R.id.tv_text);
        tv_text.setText(riddleBean.getLeftLine());
        tv_tip_text.setText(riddleBean.getTips());
        setTextVisible(false);
        final Button bt = root.findViewById(R.id.bt_startAnswer);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.setClickable(false);
                ((MainActivity) getActivity()).startCountDown();
                setTextVisible(true);
                TTSUtils.getInstance(getContext()).startSpeak(riddleBean.getLeftLine(), null);
                IseUtils.getInstance(getContext()).startIseEvaluating(riddleBean.getRightLine(), new myEvaluatorListener());
            }
        });
    }

    private void setTextVisible(boolean b) {
        if (tv_text==null||tv_tip_text==null||tv_tip==null) return;
        int visible = b ? View.VISIBLE : View.INVISIBLE;
        tv_tip_text.setVisibility(visible);
        tv_text.setVisibility(visible);
        tv_tip.setVisibility(visible);
    }

    public void speak() {
        if (riddleBean != null)
            TTSUtils.getInstance(getContext()).startSpeak(riddleBean.getLeftLine(), null);
    }

    //评测回调
    class  myEvaluatorListener implements EvaluatorListener{

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {
            ToastUtils.getInstance(getContext().getApplicationContext()).showShortToast("检测到开始回答..");
        }

        @Override
        public void onEndOfSpeech() {
            ToastUtils.getInstance(getContext().getApplicationContext()).showShortToast("检测到结束回答..");
        }

        @Override
        public void onResult(EvaluatorResult evaluatorResult, boolean isLast) {
            if (isLast) {
                String resultStr = evaluatorResult.getResultString();
                Log.d("TAG", "onResult: " + resultStr);
                if (!TextUtils.isEmpty(resultStr)) {
                    XmlResultParser resultParser = new XmlResultParser();
                    Result result = resultParser.parse(resultStr);
                    if (result.total_score > 3.5) {
                        ToastUtils.getInstance(getContext()).showShortToast("回答正确：" + result.total_score);
                        TTSUtils.getInstance(getContext()).startSpeak("回答正确", null);
                    } else {
                        ToastUtils.getInstance(getContext()).showShortToast("回答错误：" + result.total_score);
                        TTSUtils.getInstance(getContext()).startSpeak("回答错误", null);
                    }
                }
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    }
}
