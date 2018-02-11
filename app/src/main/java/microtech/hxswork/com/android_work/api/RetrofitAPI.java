package microtech.hxswork.com.android_work.api;


import microtech.hxswork.com.android_work.InfoRes.LoginRes;
import microtech.hxswork.com.android_work.bean.HomeBean;
import microtech.hxswork.com.commom.basebean.BaseResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by admin on 2016/11/8.
 */

public interface RetrofitAPI {

    String SERVER_URL = "http://192.168.1.190/testapi/index.php";
    @GET
    Observable<ResponseBody> doGet(@Header("Authorization") String token, @Url String url);

    @POST
    Observable<ResponseBody> doPost(@Header("Authorization") String token, @Url String url, @Body RequestBody requestBody);

    @FormUrlEncoded
    @POST(SERVER_URL)
    Observable<BaseResponse<LoginRes>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST(SERVER_URL)
    Observable<BaseResponse> getVerCode(@Field("phone") String phone);


    @GET(SERVER_URL)
    Observable<BaseResponse<HomeBean>> getHomeInfo();

}
