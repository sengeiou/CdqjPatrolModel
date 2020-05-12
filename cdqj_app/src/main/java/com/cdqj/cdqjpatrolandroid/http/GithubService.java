package com.cdqj.cdqjpatrolandroid.http;

import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.cdqj.cdqjpatrolandroid.utils.NetUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ljd on 3/25/16.
 */
public class GithubService {

    private GithubService() { }
    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    private static volatile OkHttpClient sOkHttpClient;

    public static <T>T createRetrofitService(final Class<T> service) {
       /* HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferencesUtil.getString(Constant.REQUEST_ADDRESS))
                .build();
        return retrofit.create(service);
    }

    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (GithubService.class) {
                Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"),
                        1024 * 1024 * 100);
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(40, TimeUnit.SECONDS)
                            .readTimeout(40, TimeUnit.SECONDS)
                            .writeTimeout(40, TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor).build();
                }
            }
        }
        return sOkHttpClient;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        if (!NetUtil.isNetWorkConnectted()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetUtil.isNetWorkConnectted()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    private static final Interceptor mLoggingInterceptor = chain -> {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        return response;
    };
}
