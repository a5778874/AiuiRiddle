package com.example.zzh.aiuiriddle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zzh.aiuiriddle.R;
import com.example.zzh.aiuiriddle.aiui.TTSUtils;
import com.example.zzh.aiuiriddle.entity.RiddleBean;
import com.example.zzh.aiuiriddle.utils.ToastUtils;

import java.util.List;
import java.util.Random;

public class MyPagerAdapter extends PagerAdapter {
    private TextView tv_text, tv_tip_text, tv_tip;

    private List<RiddleBean> riddleBeanList;
    private Context context;

    public MyPagerAdapter(Context context, List<RiddleBean> riddleBeanList) {
        this.riddleBeanList = riddleBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return riddleBeanList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        Log.d("TAG", "instantiateItem: " + position);
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.pager_riddle, null);

        RiddleBean riddleBean = riddleBeanList.get(position);
        final String leftLine = riddleBean.getLeftLine();       //上联
        final String rightLine = riddleBean.getRightLine();     //下联
        final String tipText = riddleBean.getTips();            //提示


        tv_text = relativeLayout.findViewById(R.id.tv_text);
        tv_tip_text = relativeLayout.findViewById(R.id.tv_tip_text);
        tv_tip = relativeLayout.findViewById(R.id.tv_tip);

        TTSUtils.getInstance(context).startSpeak(leftLine, null);

        tv_text.setText(leftLine);
        tv_tip_text.setText(tipText);

        ((Button) relativeLayout.findViewById(R.id.bt_startAnswer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextVisible(true);
                Log.d("TAG", "onClick: " + rightLine);

            }
        });

        relativeLayout.setId(R.id.item_id);
        container.addView(relativeLayout);
        return relativeLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }

    private void setTextVisible(boolean b) {
        if (tv_text==null||tv_tip_text==null||tv_tip==null) return;

        int visible = b ? View.VISIBLE : View.INVISIBLE;
        tv_tip_text.setVisibility(visible);
        tv_text.setVisibility(visible);
        tv_tip.setVisibility(visible);
        Log.d("TAG", "setTextVisible: "+visible);
    }


}
