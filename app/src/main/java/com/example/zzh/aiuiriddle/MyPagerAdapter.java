package com.example.zzh.aiuiriddle;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private List<RiddleBean> riddleBeanList;

    public MyPagerAdapter(List<RiddleBean> riddleBeanList) {
        this.riddleBeanList=riddleBeanList;
    }

    @Override
    public int getCount() {
        return riddleBeanList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.pager_riddle, null);
        //new LinearLayout(container.getContext());
        TextView textView = (TextView) relativeLayout.findViewById(R.id.tv_text);
        textView.setText(position +":"+ riddleBeanList.get(position).getLeftLine());
        relativeLayout.setId(R.id.item_id);
//        switch (position) {
//            case 0:
//                relativeLayout.setBackgroundColor(Color.parseColor("#2196F3"));
//                break;
//            case 1:
//                relativeLayout.setBackgroundColor(Color.parseColor("#673AB7"));
//                break;
//            case 2:
//                relativeLayout.setBackgroundColor(Color.parseColor("#009688"));
//                break;
//            case 3:
//                relativeLayout.setBackgroundColor(Color.parseColor("#607D8B"));
//                break;
//            case 4:
//                relativeLayout.setBackgroundColor(Color.parseColor("#F44336"));
//                break;
//        }
        container.addView(relativeLayout);
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return relativeLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }
}
