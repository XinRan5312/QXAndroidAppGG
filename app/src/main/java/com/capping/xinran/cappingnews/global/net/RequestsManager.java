package com.capping.xinran.cappingnews.global.net;

import com.capping.xinran.cappingnews.BuildConfig;
import com.capping.xinran.cappingnews.bean.OneBean;
import com.capping.xinran.cappingnews.global.EngloryApplication;
import com.capping.xinran.cappingnews.global.net.fastjson.FastJsonConverterFactory;
import com.capping.xinran.cappingnews.global.net.retrofit.AddHeaderAndCookieInterceptor;
import com.capping.xinran.cappingnews.global.net.retrofit.ApiService;
import com.capping.xinran.cappingnews.global.net.retrofit.HttpResponseFunc;
import com.capping.xinran.cappingnews.global.net.retrofit.ReceivedCookiesInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by qixinh on 16/9/22.
 */
public class RequestsManager {
    private ApiService apiService;
    private Retrofit retrofit;
    public RequestsManager() {
        init();
    }

    private void init() {
        //http://www.webservicex.net/globalweather.asmx/GetWeather?CityName=dalian&CountryName=china
                retrofit= new Retrofit.Builder()
                .baseUrl("http://www.webservicex.net")
                .addConverterFactory(new FastJsonConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build();
        apiService =createApi(ApiService.class);
    }
    public  <T> T createApi(Class<T> clazz) {

        return retrofit.create(clazz);
    }

    public Observable<List<String>> getNamesResultObserver(String user, String id) {
        return apiService.getNamesResultObserver(user, id)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new HttpResponseFunc<List<String>>());
    }

    public Observable<List<String>> postNamesResultObserver(OneBean bean) {
        return apiService.postNamesResultObserver(new OneBean())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new HttpResponseFunc<List<String>>());
    }
    public Observable<List<String>> getWeather(){
       return apiService.postNamesResultObserver(new OneBean())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new HttpResponseFunc<List<String>>());
    }
    private OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new ReceivedCookiesInterceptor(EngloryApplication.getAppContext()))
                .addInterceptor(new AddHeaderAndCookieInterceptor(EngloryApplication.getAppContext()))
                .addInterceptor(provideHttpLoggingInterceptor())
                .build();
        return client;
    }

    //刚开始在考虑其他几个参数都可以在本类中提供，如http日志
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }
}
