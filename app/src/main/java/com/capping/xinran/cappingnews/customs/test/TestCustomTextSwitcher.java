package com.capping.xinran.cappingnews.customs.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.customs.views.CustomTextSwitcher;

import java.util.ArrayList;

/**
 * Created by houqixin on 2016/11/7.
 */
public class TestCustomTextSwitcher extends Activity {
    private CustomTextSwitcher TextView;
    private ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_auto_scroll_textview);
        init();
    }

    private void init() {
        TextView = (CustomTextSwitcher) findViewById(R.id.tv_auto_scroll);
        titleList.add("你是天上最受宠的一架钢琴");
        titleList.add("我是丑人脸上的鼻涕");
        titleList.add("你发出完美的声音");
        titleList.add("我被默默揩去");
        titleList.add("你冷酷外表下藏着诗情画意");
        titleList.add("我已经够胖还吃东西");
        titleList.add("你踏着七彩祥云离去");
        titleList.add("我被留在这里");
        TextView.setTextList(titleList);
        TextView.setText(26, 5, Color.RED);//设置属性
        TextView.setTextStillTime(3000);//设置停留时长间隔
        TextView.setAnimTime(300);//设置进入和退出的时间间隔
        TextView.setOnItemClickListener(new CustomTextSwitcher.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(TestCustomTextSwitcher.this, "点击了 : " + titleList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TextView.stopAutoScroll();
    }
}

