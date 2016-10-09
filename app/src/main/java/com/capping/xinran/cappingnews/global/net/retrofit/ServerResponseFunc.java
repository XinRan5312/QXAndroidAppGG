package com.capping.xinran.cappingnews.global.net.retrofit;

import retrofit2.Response;
import rx.functions.Func1;


/**
 * Created by qixinh on 16/9/22.
 */
public class ServerResponseFunc<T> implements Func1<Response<T>, T> {
    @Override
    public T call(Response<T> reponse) {
        //对返回码进行判断，如果不是0，则证明服务器端返回错误信息了，便根据跟服务器约定好的错误码去解析异常
        if (reponse.code() != 0) {
            //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
            throw new ServerException(reponse.code(),reponse.message());
        }
        //服务器请求数据成功，返回里面的数据实体
        return reponse.body();
    }
}
