package com.capping.xinran.cappingnews.base.webview;

import android.content.Context;

/**
 * Created by qixinh on 16/9/20.
 */
public class JsToNative {
    private Context context;
    public JsToNative(){}
    public JsToNative(Context context){
        this.context=context;
    }
    //以下返回都是json字符串

    /**
     *
     * @return 返回图片地址和数据库保存的图片值
     */
    public String uploadImageInfo(){

        return "";
    }

    /**
     *
     * @return 返回用户地址信息（包括电话 地址 经纬度）
     */
    public String userAddressInfo(){

        return "";
    }

    /**
     *
     * @return 返回成功失败状态
     */
    public String receiveUeserId(){

        return "";
    }

    /**
     *
     * @return 返回成功失败状态
     */
    public String orderPay(String orderid, float payMoney){

        return "";
    }

    /**
     * 根据协议规定的urlData，然后Nativie解析后跳转到相应的Nativie界面
     * @param urlData
     */
    public void toPage(String urlData){

    }
}
