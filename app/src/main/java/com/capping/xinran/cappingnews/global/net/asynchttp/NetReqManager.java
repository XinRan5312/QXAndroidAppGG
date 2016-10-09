package com.capping.xinran.cappingnews.global.net.asynchttp;

/**
 * 对外网络请求类
 * Created by qixinh on 16/9/29.
 */
public class NetReqManager{
    private static NetReqManager instance;
    private NetApiFlagInterface netApiManager;
    private static  Object clock=new Object();
    private NetReqManager(){
        netApiManager=new AsyncHttpNetApi(AsyncHttpConfig.getDefaultHttClient());
    }
    public static NetReqManager newInstance(){
            if(instance==null){
                synchronized (clock){
                    instance=new NetReqManager();
                }
            }
        return instance;
    }

    public NetApiFlagInterface getNetApiManager() {
        return netApiManager;
    }
}
