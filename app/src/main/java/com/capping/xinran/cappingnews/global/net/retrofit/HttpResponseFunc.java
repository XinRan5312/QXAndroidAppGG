package com.capping.xinran.cappingnews.global.net.retrofit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by qixinh on 16/9/22.
 */
public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
