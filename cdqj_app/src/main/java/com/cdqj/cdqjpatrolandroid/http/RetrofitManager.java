package com.cdqj.cdqjpatrolandroid.http;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by lyf on 2017/6/15.
 * Retrofit的网络请求组装URL及属性设置类
 * @author lyf
 * Retrofit管理类
 */
public class RetrofitManager {

    /**
     * 超时
     */
    public static final int TIME_OUT = 60;

    /**
     * 短缓存有效期为1分钟
     */
    public static final int CACHE_STALE_SHORT = 60;
    /**
     * 长缓存有效期为7天
     */
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     */
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
     */
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    public static OkHttpClient mOkHttpClient;
    public static RetrofitManager mApiRetrofit;
    private Retrofit mRetrofit;

    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
    private Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        // NetworkUtils.isAvailableByPing()
        if (NetworkUtils.isConnected()) {
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                    .build();
        }
    };

    private RetrofitManager() {
        if (StringUtils.isTrimEmpty(PreferencesUtil.getString(Constant.REQUEST_ADDRESS))) {
            PreferencesUtil.putString(Constant.REQUEST_ADDRESS, Constant.REQUEST_ADDRESS_DEFAULT);
        }
        if (StringUtils.isTrimEmpty(PreferencesUtil.getString(Constant.FILE_SERVICE_PATH))) {
            PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, Constant.FILE_SERVICE_PATH_DEFAULT);
        }
        initOkHttpClient();
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        mRetrofit= new Retrofit.Builder()
                .baseUrl(PreferencesUtil.getString(Constant.REQUEST_ADDRESS))
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(rxAdapter)
                .client(mOkHttpClient)
                .build();
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLogger());
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
                    HttpCommonInterceptor.Builder builder = new HttpCommonInterceptor.Builder();
                    builder.addHeaderParams("Content-Type","application/json")
                            .addHeaderParams("Accept","application/json")
                            // XMLHttpRequest ： X-Requested-With 用于判断 是ajax，加上就能返回  错误  json
                            .addHeaderParams("XMLHttpRequest","X-Requested-With")
                            .addHeaderParams("qjtoken", PreferencesUtil.getString(Constant.TOKEN));
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(builder.build())
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    public static RetrofitManager getInstance() {
        if (mApiRetrofit == null) {
            synchronized (Object.class) {
                if (mApiRetrofit == null) {
                    mApiRetrofit = new RetrofitManager();
                }
            }
        }
        return mApiRetrofit;
    }

    public static PatrolApiService getApiService() {
        if (StringUtils.isTrimEmpty(PreferencesUtil.getString(Constant.REQUEST_ADDRESS))) {
            PreferencesUtil.putString(Constant.REQUEST_ADDRESS, Constant.REQUEST_ADDRESS_DEFAULT);
        }
        if (StringUtils.isTrimEmpty(PreferencesUtil.getString(Constant.FILE_SERVICE_PATH))) {
            PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, Constant.FILE_SERVICE_PATH_DEFAULT);
        }
        if (mOkHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            Cache cache = new Cache(new File(DirConfig.HTTP_CACHE_STATIC), 1024 * 1024 * 100);
            Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"),
                    1024 * 1024 * 100);
            HttpCommonInterceptor.Builder builder = new HttpCommonInterceptor.Builder();
            builder.addHeaderParams("Content-Type","application/json")
                    .addHeaderParams("Accept","application/json")
            // XMLHttpRequest ： X-Requested-With 用于判断 是ajax，加上就能返回  错误  json
                    .addHeaderParams("XMLHttpRequest","X-Requested-With")
                    .addHeaderParams("qjtoken", PreferencesUtil.getString(Constant.TOKEN));
            mOkHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(builder.build())
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PreferencesUtil.getString(Constant.REQUEST_ADDRESS))
//                .baseUrl(Constant.REQUEST_ADDRESS_DEFAULT)
                .client(mOkHttpClient)
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(rxAdapter)
                .build();
        return retrofit.create(PatrolApiService.class);
    }


    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T> service类型
     * @return service
     */
    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }
}
