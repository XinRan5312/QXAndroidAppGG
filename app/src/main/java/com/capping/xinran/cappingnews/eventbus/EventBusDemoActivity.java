package com.capping.xinran.cappingnews.eventbus;

import android.app.Activity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by houqixin on 2016/11/28.
 * * 原理解析：
 * http://blog.csdn.net/lmj623565791/article/details/40920453
 *
 * 介绍了register和post；大家获取还能想到一个词sticky，在register中，如何sticky为true，会去stickyEvents去查找事件，然后立即去post；

 那么这个stickyEvents何时进行保存事件呢？

 其实evevntbus中，除了post发布事件，还有一个方法也可以：postSticky(Object event)
 也就是说postSticky不是立马发布，只是一个预发布，只要有人注册了registerSticky(register),才发布
 新版本的EnventBus好像没有registerSticky方法了
 */
public class EventBusDemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 与发布者在同一个线程
     * @param msg 事件1
     */
    public void onEvent(ZCEventBeanMsg msg){
        String content = msg.getContent()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        System.out.println("onEvent(MsgEvent1 msg)收到" + content);
    }

    /**
     * 执行在主线程。
     * 非常实用，可以在这里将子线程加载到的数据直接设置到界面中。
     * @param msg 事件1
     */
    public void onEventMainThread(ZCEventBeanMsg msg){
        String content = msg.getName()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        System.out.println("onEventMainThread(MsgEvent1 msg)收到" + content);

    }

    /**
     * 执行在子线程，如果发布者是子线程则直接执行，如果发布者不是子线程，则创建一个再执行
     * 此处可能会有线程阻塞问题。
     * @param msg 事件1
     */
    public void onEventBackgroundThread(ZCEnventBeanWriteFile msg){
        String content = msg.getFileName()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        System.out.println("onEventBackgroundThread(MsgEvent1 msg)收到" + content);
    }

    /**
     * 执行在在一个新的子线程
     * 适用于多个线程任务处理， 内部有线程池管理。
     * @param msg 事件1
     */
    public void onEventAsync(ZCEnventBeanWriteFile msg){
        String content = msg.getFileSize()
                + "\n ThreadName: " + Thread.currentThread().getName()
                + "\n ThreadId: " + Thread.currentThread().getId();
        System.out.println("onEventAsync(MsgEvent1 msg)收到" + content);
    }

}
