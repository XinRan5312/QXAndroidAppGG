package com.capping.xinran.cappingnews.base.backfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.capping.xinran.cappingnews.R;

/**
 * Created by qixinh on 16/10/12.
 */
public class QFragemtOne extends BackHandledFragment {

    private boolean hadIntercept;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_one, null);
    }

    @Override
    public boolean onBackPressed() {
        if(hadIntercept){
            //交给Actvity处理返回事件
            return false;
        }else{
            //Fragment处理back事件
            Toast.makeText(getActivity(), "Click From QFragmentOne", Toast.LENGTH_SHORT).show();
            hadIntercept = true;
            return true;
        }
    }

}
