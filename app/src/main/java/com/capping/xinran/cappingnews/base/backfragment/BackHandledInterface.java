package com.capping.xinran.cappingnews.base.backfragment;

/**
 * Created by qixinh on 16/10/12.
 * 需要处理back事件的Fragment的宿主Activity要实现此接口
 */
public interface BackHandledInterface {
    public abstract void setSelectedFragment(BackHandledFragment selectedFragment);
}
