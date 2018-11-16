package com.example.zzh.aiuiriddle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private UltraViewPager ultraViewPager;
    private MyPagerAdapter pagerAdapter;
    private UltraViewPager.Orientation gravity_indicator;
    private List<RiddleBean> riddleLists=new ArrayList<>();

    private String[] poetrys = new String[]{
            "千岩瀑布经霜卷,一洞梅花带雪香-《游萝峰竭前贤宋大夫》 [清] 区丕烈",
            "十里梅花浑似雪,萝岗香雪映朝阳-[近代] 郭沫若",
            "墙角数枝梅,凌寒独自开-《梅花》 [宋] 王安石",
            "遥知不是雪,为有暗香来-《梅花》 [宋] 王安石"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initView();

    }

    private void initDatas() {
        for (int i=0;i<poetrys.length;i++){
            String[] split = poetrys[i].split("-");
            String tips=split[1];

            String[] textSplit = split[0].split(",");
            String leftLine=textSplit[0];
            String rightLine=textSplit[1];

            Log.d("TAG", "riddle: "+leftLine+","+rightLine+"-"+tips);
            riddleLists.add(new RiddleBean(leftLine,rightLine,tips));
        }

    }

    private void initView() {
        ultraViewPager=findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        pagerAdapter = new MyPagerAdapter(riddleLists);
        ultraViewPager.setAdapter(pagerAdapter);
        ultraViewPager.setInfiniteRatio(100);
        ultraViewPager.setInfiniteLoop(true);
        gravity_indicator = UltraViewPager.Orientation.HORIZONTAL;
        ultraViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // Log.d("TAG", "onPageScrolled: "+position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG", "onPageSelected: "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               // Log.d("TAG", "onPageScrollStateChanged: "+state);
            }
        });
    }

    //换一题
    public void changeNext(View view) {
        ultraViewPager.scrollNextPage();
    }
}
