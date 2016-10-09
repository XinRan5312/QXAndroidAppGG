package com.capping.xinran.cappingnews.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.base.QxBaseAdapter;
import com.capping.xinran.cappingnews.global.tabview.QxBaseListViewContainer;
import com.capping.xinran.cappingnews.global.tabview.QxBaseScrollView;
import com.capping.xinran.cappingnews.global.tabview.QxListView;
import com.capping.xinran.cappingnews.global.tabview.fragment.QxTapBaseFragment;
import com.capping.xinran.cappingnews.test.utils.QxUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by qixinh on 16/4/15.
 */
public class QxTestFragment extends QxTapBaseFragment implements QxBaseListViewContainer.OnHeaderRefreshListener,
        QxScrollView.IChangeXiDing, QxBaseListViewContainer.ScrollStateLinsener, QxBaseScrollView.OnAtBottomListener, QxListView.OnLoadMoreListener {
    private QxBaseAdapter myAdapter;

    private QxBaseListViewContainer mQxListViewContainer;
    private QxListView listviewOne;
    private List<Integer> data;

    private QxBaseScrollView rootview;
    private TextView mTv;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_scroll_xiding_main, container, false);
    }

    @Override
    public void dealView(View view) {
        LinearLayout linear = (LinearLayout) findView(R.id.container_listview);
        QxUtils.setLinearLayoutParams(linear, getActivity());
        LinearLayout cardContainer = findView(R.id.card_container);
        mQxListViewContainer = findView(R.id.list_view_root_container);
        listviewOne = findView(R.id.list_view_first);
        rootview = findView(R.id.list_view_top_container);
        rootview.setBottomListener(this);
        rootview.setChangeXiDing(this);
        mTv = findView(R.id.list_view_tile_top);
        data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(i);
        }
        TextView tv = null;
        for (int i = 0; i < 10; i++) {
            tv = new TextView(this.getActivity());
            tv.setText("Card==" + i);
            cardContainer.addView(tv, i);

        }
        myAdapter = new QxAdapter(getActivity(), data);
        listviewOne.setAdapter(myAdapter);

        mQxListViewContainer.setOnHeaderRefreshListener(this);
        mQxListViewContainer.setScrollStateLinsener(this);
        mQxListViewContainer.setLastUpdated(new Date().toLocaleString());

        listviewOne.setOnLoadMoreListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        rootview.post(new Runnable() {
            @Override
            public void run() {
                rootview.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void onRegularResume() {

    }

    @Override
    public void onFirstResume() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void changeXiDingShow(int type) {
        if (type == 2) {
            mTv.setText("我是listView Title");
        }
    }

    @Override
    public void changeXiDingHide(int type) {
        if (type == 2) {
            mTv.setText("");
        }
    }

    @Override
    public void onScrollUp() {

    }

    @Override
    public void onScrollDown() {

    }

    @Override
    public void atBottom() {
        listviewOne.setIsAtBottom(true);
    }

    @Override
    public void prepareToLoadMore() {
        mTv.setText("prepareToLoadMore()");
    }

    @Override
    public void onLoadMoreData() {
        mQxListViewContainer.postDelayed(new Runnable() {

            @Override
            public void run() {
                myAdapter.addData(data);
                onLoadMoreDataComplete();
            }

        }, 3000);
        mTv.setText("onLoadMoreData()...");
    }

    @Override
    public void onLoadMoreDataComplete() {
        mTv.setText("onLoadMoreDataComplete()");
    }

    @Override
    public void onHeaderRefresh(QxBaseListViewContainer view) {
        mQxListViewContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                mQxListViewContainer.onHeaderRefreshComplete("更新于:"
                        + Calendar.getInstance().getTime().toLocaleString());
                mQxListViewContainer.onHeaderRefreshComplete();

                Toast.makeText(getActivity(), "数据刷新完成!", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }

    @Override
    public void showErrorTip(EditText editText, String message) {

    }

    @Override
    public PopupWindow showTipView(View view) {
        return null;
    }

    @Override
    public void processAgentPhoneCall(String phoneNum) {

    }

    @Override
    public FragmentManager getV4FragmentManager() {
        return null;
    }

    @Override
    public Handler getHandler() {
        return null;
    }
}
