package com.capping.xinran.cappingnews.global.net.retrofit;

import com.capping.xinran.cappingnews.bean.OneBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by qixinh on 16/9/22.
 */
public interface ApiService {
    @GET("/globalweather.asmx/GetWeather?CityName=dalian&CountryName=china")
    Observable<List<String>> getWeather();
    @GET("/users/{user}/{id}/wr")
    Observable<List<String>> getNamesResultObserver(@Path("user") String user, @Path("id") String id);

    @POST("/users/wr")
    Observable<List<String>> postNamesResultObserver(@Body OneBean bean);

    /**
     * 注意采用一个POJO作为请求体，它会被RestAdapter进行转换。同时POST方式可以传入回调。
     * FORM ENCODED AND　MULTIPART表单域与文件上传
     *
     * @FormUrlEncoded修饰表单域，每个表单域子件key-value采用@Field修饰
     */
    @FormUrlEncoded
    @POST("/user/edit")
    Observable<Integer> updateForm(@Field("name") String first, @Field("age") String last);


    /**
     * @Multipart修饰用于文件上传，每个Part元素用@Part修饰:
     * @Part(“fileDes”) String des 可以加一些描述信息(可以不加)
     * @Part(“file\”; filename=\”1.txt”) 格式不变，只需将1.text 对应的替换为你想在服务器生成的文件名称
     * 如果想传多个文件，多次请求，当然，也可以像表单一样(还没弄好)
     * 当然，上面这种办法的灵活性差了点，我们可以选择下面这种写法
     */
    @Multipart
    @POST("/fileabout.php")
    Observable<String> uploadFile(@Part("fileName") String des,
                                  @Part("file\"; filename=\"1.txt") RequestBody file);

    @Multipart
    @POST("/fileabout.php")
    Observable<Integer> uploadFile(@PartMap Map<String, RequestBody> params);

}
