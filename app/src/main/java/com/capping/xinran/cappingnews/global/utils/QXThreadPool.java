package com.capping.xinran.cappingnews.global.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by houqixin on 2017/1/4.
 */
public class QXThreadPool {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static QXThreadPool sInstance=new QXThreadPool();
    private QXThreadPool(){}
    public QXThreadPool newInstance(){
        return sInstance;
    }
    private  final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCounter = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "MSGlobalQueue #" + mCounter.getAndIncrement());
        }
    };

    private final BlockingQueue<Runnable> mWorkPoolQueue = new LinkedBlockingQueue<>(128);
    private  final Executor sThreadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, mWorkPoolQueue, sThreadFactory
    );
    private  final ExecutorService mThreadPoolExecutor = Executors.newCachedThreadPool(sThreadFactory);
    private  final ScheduledExecutorService sScheduledExecutor = Executors.newScheduledThreadPool(CORE_POOL_SIZE, sThreadFactory);
    public  void postCommon(Runnable task) {
        sThreadPoolExecutor.execute(task);
    }

    /**
     * （1）schedule方法：下一次执行时间相对于 上一次 实际执行完成的时间点 ，因此执行时间会不断延后
     （2）scheduleAtFixedRate方法：下一次执行时间相对于上一次开始的 时间点 ，因此执行时间不会延后，存在并发性
     * @param task
     * @param milliSeconds
     * scheduleWithFixedDelay从字面意义上可以理解为就是以固定延迟（时间）来执行线程任务，它实际上是不管线程任务的执行时间的，
     * 每次都要把任务执行完成后再延迟固定时间后再执行下一次。

    而scheduleFixedRate呢，是以固定频率来执行线程任务，固定频率的含义就是可能设定的固定时间不足以完成线程任务，
    但是它不管，达到设定的延迟时间了就要执行下一次了。
     */
    public void postScheduled(Runnable task,long milliSeconds){
        sScheduledExecutor.schedule(task,milliSeconds,TimeUnit.MILLISECONDS);
    }
    public void postScheduledFixRate(Runnable task,long initDelay,long milliSeconds){
        sScheduledExecutor.scheduleAtFixedRate(task,initDelay,milliSeconds,TimeUnit.MILLISECONDS);
    }
    public void postScheduledWithFixRate(Runnable task,long initDelay,long milliSeconds){
        sScheduledExecutor.scheduleWithFixedDelay(task,initDelay,milliSeconds,TimeUnit.MILLISECONDS);
    }
}
