package com.capping.xinran.cappingnews.base.backfragment;

import com.capping.xinran.cappingnews.base.BaseActivity;

/**
 * Created by qixinh on 16/10/12.
 */
public class FragmentBackActivity extends BaseActivity implements BackHandledInterface {
    private BackHandledFragment mBackHandedFragment;
    @Override
    protected void initData() {

    }
    //在继承BackHandledFragment的Fragment中调用此方法
    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment=selectedFragment;
    }
    @Override
    public void onBackPressed() {
        //mBackHandedFragment.onBackPressed()先走其中的Fragment中的自定义的onBackPressed()
        //其处理完了再根据结果宿主Activity确定自己处理不处理
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }
    }
}
