package com.capping.xinran.cappingnews.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.global.tabview.downtap.QxBaseTabActivity;
import com.capping.xinran.cappingnews.global.tabview.downtap.TabIndicator;
import com.capping.xinran.cappingnews.test.fragments.QxCartFragment;
import com.capping.xinran.cappingnews.test.fragments.QxCenterFragment;
import com.capping.xinran.cappingnews.test.fragments.QxMainFragment;
import com.capping.xinran.cappingnews.test.fragments.QxShopFragment;


public class TestMainActivity extends QxBaseTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_actvity);
        addTabs();

    }

    private void addTabs() {
        addTab("Main", "Main", R.drawable.icon_uc, R.id.qx_fragment_main, QxMainFragment.class, null);

        addTab("Shop", "Shop", R.drawable.background_tab, R.id.qx_fragment_shopping, QxShopFragment.class, null);
        addTab("Cart", "Cart", R.drawable.icon_uc, R.id.qx_fragment_car, QxCartFragment.class, null);
        addTab("Center", "Center", R.drawable.background_tab, R.id.qx_fragment_center, QxCenterFragment.class, null);

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected TabIndicator.IndicatorFactory indicatorFactory() {
        return new MainPageIndicatorFactory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentTabByTag(getPreviousTabTag());
    }

    private static class MainPageIndicatorFactory implements TabIndicator.IndicatorFactory {

        @Override
        public TabIndicator.IndicatorView createIndicatorView(Context context, int position) {

            if (position == 3) {
                return new ActivityIndicator(context, R.layout.home_tab_item_view, position);
            } else {
                return new ActivityIndicator(context, R.layout.home_tab_item_view, position);
            }

        }
    }
}
