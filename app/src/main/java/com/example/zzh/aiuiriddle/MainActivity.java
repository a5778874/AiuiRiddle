package com.example.zzh.aiuiriddle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zzh.aiuiriddle.adapter.FragmentAdapter;
import com.example.zzh.aiuiriddle.adapter.MyPagerAdapter;
import com.example.zzh.aiuiriddle.aiui.IseUtils;
import com.example.zzh.aiuiriddle.aiui.TTSUtils;
import com.example.zzh.aiuiriddle.entity.RiddleBean;
import com.example.zzh.aiuiriddle.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseAcitivity {
    private TextView tv_countdown;
    private CountDownTimer countDownTimer;
    private UltraViewPager ultraViewPager;
    private MyPagerAdapter pagerAdapter;
    private UltraViewPager.Orientation gravity_indicator;
    private List<RiddleBean> riddleLists = new ArrayList<>();
    private int curpos = 0;

    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    ;

    private static final String[] poetrys = new String[]{
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
        requestPermissions();
        TTSUtils.getInstance(this); //初始化语音合成
        IseUtils.getInstance(this); //初始化语音评测
    }

    private void initDatas() {

        for (int i = 0; i < poetrys.length; i++) {
            String[] split = poetrys[i].split("-");
            String tips = split[1];

            String[] textSplit = split[0].split(",");
            String leftLine = textSplit[0];
            String rightLine = textSplit[1];

            Log.d("TAG", "riddle: " + leftLine + "," + rightLine + "-" + tips);
            // riddleLists.add();
            fragments.add(PagerFragment.newInstance(new RiddleBean(leftLine, rightLine, tips)));
        }

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

    }

    private void initView() {
        tv_countdown = findViewById(R.id.tv_countdown);
        //倒计时
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_countdown.setText((millisUntilFinished / 1000) + "");
            }

            @Override
            public void onFinish() {
                tv_countdown.setText("0");
                ToastUtils.getInstance(getApplicationContext()).showShortToast("回答超时");
                IseUtils.getInstance(MainActivity.this).cancelEvaluating();
            }
        };

        ultraViewPager = findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        pagerAdapter = new MyPagerAdapter(MainActivity.this, riddleLists);
        ultraViewPager.setAdapter(fragmentAdapter);
        ultraViewPager.setInfiniteRatio(100);
        ultraViewPager.setInfiniteLoop(true);
        gravity_indicator = UltraViewPager.Orientation.HORIZONTAL;

        ultraViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Log.d("TAG", "onPageScrolled: "+position+"...."+positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                if (fragments != null) {
                    curpos = position % fragments.size();
                    //((PagerFragment) fragments.get(curpos)).speak();
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //  Log.d("TAG", "onPageScrollStateChanged: "+state);
            }
        });

        curpos = 400 % fragments.size();
        ultraViewPager.setCurrentItem(400);
//        ultraViewPager.post(new Runnable() {
//            @Override
//            public void run() {
//                ((PagerFragment) fragments.get(curpos)).speak();
//            }
//        });

    }

    //开始计时
    public void startCountDown() {
        if (countDownTimer != null)
            countDownTimer.start();
    }

    //计时取消
    public void cancelCountDown() {
        if (countDownTimer != null)
            countDownTimer.cancel();

    }


    //换一题
    public void changeNext(View view) {
        ultraViewPager.scrollNextPage();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(
                        Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("TAG", permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d("TAG", permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d("TAG", permission.name + " is denied.");
                        }
                    }
                });
    }
}
