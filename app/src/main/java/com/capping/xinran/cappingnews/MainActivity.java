package com.capping.xinran.cappingnews;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.capping.xinran.cappingnews.base.BaseActivity;
import com.capping.xinran.cappingnews.base.BaseOnClickLisener;
import com.capping.xinran.cappingnews.global.findview.FindView;
import com.capping.xinran.cappingnews.global.imageloader.QImageLoaderApi;
import com.capping.xinran.cappingnews.global.imageloader.QImgLoaderManager;
import com.capping.xinran.cappingnews.global.net.RequestsManager;
import com.capping.xinran.cappingnews.global.net.retrofit.QSimpleSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {
    @FindView(R.id.tv_go)
    private TextView tv;
    @FindView(R.id.img_test)
    private ImageView img;
    private RequestsManager requestsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main_main);
        requestsManager = new RequestsManager();


    }

    @Override
    protected void initData() {
        QImgLoaderManager.newInstance().getImgloaderManager().loaderNetImg("http://img.lanrentuku.com/img/allimg/1211/5-121116134045.gif", img);
        tv.setOnClickListener(new BaseOnClickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestsManager.getWeather()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new QSimpleSubscriber<List<String>>(MainActivity.this) {
                            @Override
                            protected void onNextDo(List<String> strings) {
                                Log.e("Franky", "onNextDo");
                            }
                        });
            }
        }));
//        loaderNetImg("http://img.lanrentuku.com/img/allimg/1211/5-121116134045.gif", img);
        //QImgLoader.loaderImgNet(this,"http://img.lanrentuku.com/img/allimg/1211/5-121116134045.gif", img);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
