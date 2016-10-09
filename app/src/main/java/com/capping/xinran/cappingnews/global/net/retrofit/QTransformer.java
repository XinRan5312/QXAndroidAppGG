package com.capping.xinran.cappingnews.global.net.retrofit;


import retrofit2.Response;
import rx.Observable;

/**
 * Created by qixinh on 16/9/22.
 */
public class QTransformer<E,R> implements Observable.Transformer<E,R> {


    @Override
    public Observable<R> call(Observable<E> eObservable) {
        return null;
    }
}

