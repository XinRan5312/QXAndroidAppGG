package com.capping.xinran.cappingnews.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by houqixin on 2016/11/28.
 * 原理解析：
 * http://blog.csdn.net/lmj623565791/article/details/40920453
 *
 * 介绍了register和post；大家获取还能想到一个词sticky，在register中，如何sticky为true，会去stickyEvents去查找事件，然后立即去post；

 那么这个stickyEvents何时进行保存事件呢？

 其实evevntbus中，除了post发布事件，还有一个方法也可以：postSticky(Object event)
 也就是说postSticky不是立马发布，只是一个预发布，只要有人注册了register(register),才发布

 */
public class ZCEventBusPostDemo {
    public static void postInThread(){
        new Thread(){
            @Override
            public void run() {
                ZCEventBeanMsg msg=getFromNet();
                EventBus.getDefault().post(msg);
            }
        };
    }

    private static ZCEventBeanMsg getFromNet() {
        return null;
    }
    public static void post(ZCEventBeanMsg msg){
        EventBus.getDefault().post(msg);
    }
}
