package microtech.hxswork.com.android_work.api;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import microtech.hxswork.com.android_work.Util.SPUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RetrofitService {
    private static final String BASE_URL = "http://192.168.21.93:8080/Chat/";
    private static RetrofitService ourInstance = new RetrofitService();
    private Retrofit mRetrofit;
    public RetrofitAPI mAPI;
    private  OkHttpClient client;

    public static RetrofitService getInstance() {
        return ourInstance;
    }

    private RetrofitService() {
        init();
    }


    public void init() {

        client = new OkHttpClient.Builder()
                .addInterceptor(new RequestInterceptor())
                .retryOnConnectionFailure(false) //是否允许失败后重复请求
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        mRetrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//加入Gson解析
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
                .build();

        mAPI = mRetrofit.create(RetrofitAPI.class);
    }


    /**
     * Retrofit的get请求封装
     * @param url 需要访问的url
     * @return 需要执行的call对象
     */
    public Observable<ResponseBody> doGet(String url) {
        return mAPI.doGet(SPUtil.getString("AUTHORIZATION", ""), url);
    }

    /**
     * Retrofit的post请求封装
     * @param url 需要访问的url
     * @param json post需要传递的参数
     * @return 需要执行的call对象
     */
    public Observable<ResponseBody> doPost(String url , String json) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        return mAPI.doPost(SPUtil.getString("AUTHORIZATION", ""), url, requestBody);

    }

    //请求头拦截
    public static class RequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("os", "Android")
                    .header("Connection", "close");
            return chain.proceed(builder.build());
        }
    }
}
