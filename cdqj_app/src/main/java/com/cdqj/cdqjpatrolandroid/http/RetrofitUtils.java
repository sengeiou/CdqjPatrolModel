package com.cdqj.cdqjpatrolandroid.http;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.loadingdialog.CustomProgressDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.loadingdialog.LoadingHelper;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.gis.util.CoordinateUtils;
import com.cdqj.cdqjpatrolandroid.http.inter.ICdToLatlon;
import com.cdqj.cdqjpatrolandroid.model.ModelAcoordResultBean;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.bean.SearchRoundRequestBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.http.inter.IAddressByPoint;
import com.cdqj.cdqjpatrolandroid.http.inter.IGetGisParameterListener;
import com.cdqj.cdqjpatrolandroid.model.BdAddressBean;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.TransformUtils;
import com.cdqj.cdqjpatrolandroid.view.ui.main.MyFragment;
import com.esri.arcgisruntime.geometry.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {

    private static CustomProgressDialog progressDialog = null;

    /**
     * 将文件路径数组封装为{@link List<MultipartBody.Part>}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key, File[] filePaths, MediaType imageType, RetrofitCallback callback) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (File file : filePaths) {
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            FileRequestBody body = new FileRequestBody(requestBody, callback);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key, file.getName(), body);
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    /**
     * 将文件路径对象封装为{@link <MultipartBody.Part}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePath  文件路径数组
     * @param imageType 文件类型
     */
    public static MultipartBody.Part file2Part(String key, File filePath, MediaType imageType, RetrofitCallback callback) {
        // 根据类型及File对象创建RequestBody（okhttp的类）
        RequestBody requestBody = RequestBody.create(imageType, filePath);
        FileRequestBody body = new FileRequestBody(requestBody, callback);
        // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
        MultipartBody.Part part = MultipartBody.Part.
                createFormData(key, filePath.getName(), body);
        return part;
    }

    /**
     * 将路径对象封装为{@link <MultipartBody.Part}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePath  文件路径数组
     * @param imageType 文件类型
     */
    public static MultipartBody.Part file2Part(String key, String filePath, MediaType imageType, RetrofitCallback callback) {
        File file = FileUtils.getFileByPath(filePath);
        return file2Part(key, file, imageType, callback);
    }

    /**
     * 将路径对象封装为{@link <MultipartBody.Part}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key, String filePaths, MediaType imageType, RetrofitCallback callback) {
        String[] filePath = filePaths.split(",");
        File[] files = new File[filePath.length];
        for (int i = 0; i < filePath.length; i++) {
            if (!StringUtils.isTrimEmpty(filePath[i])) {
                files[i] = FileUtils.getFileByPath(filePath[i]);
            }
        }
        return files2Parts(key, files, imageType, callback);
    }

    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     *
     * @param parts    Part集合
     * @param key      参数名（name属性）
     * @param value    文本内容
     * @param position 插入的位置
     */
    public static void addTextPart(List<MultipartBody.Part> parts,
                                   String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    public static Map<String, RequestBody> addTextPart(
            String key, String value) {
        Map<String, RequestBody> map = new HashMap<>();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        map.put(key, requestBody);
        return map;
    }

    /**
     * 文件上传（单个）
     *
     * @param filePath    filePath 文件地址
     * @param fileType    fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     */
    public static void updateFile(String filePath, int fileType, OnUpdateFileBackListener mOnListener, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(total, progress);
                // LogUtils.d(progress * 100 / total + "---->" + total + "---" + progress);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        String updateUrl2 = "image";
        if (fileType == Constant.IMG_TYPE) {
            updateUrl2 = "image";
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            updateUrl2 = "voice";
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            updateUrl2 = "video";
            parse = MediaType.parse("video/mp4");
        }
        MultipartBody.Part part = RetrofitUtils.file2Part("files", filePath, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadImgOnlyOne(PreferencesUtil.getString(Constant.TOKEN), "PATROL", updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（单个）
     *
     * @param filePath    filePath 文件地址
     * @param fileType    fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     * @param key         key 文件服务器key
     * @param updateUrl1  updateUrl 缺省值
     * @param updateUrl2  updateUrl 缺省值
     */
    public static void updateFile(String filePath, int fileType, OnUpdateFileBackListener mOnListener, String key, String updateUrl1, String updateUrl2, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        if (fileType == Constant.IMG_TYPE) {
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            parse = MediaType.parse("video/mp4");
        }
        MultipartBody.Part part = RetrofitUtils.file2Part(key, filePath, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadImgOnlyOne(PreferencesUtil.getString(Constant.TOKEN), updateUrl1, updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     *
     * @param filePaths   filePaths 文件地址
     * @param fileType    fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     */
    public static void updateFiles(String filePaths, int fileType, OnUpdateFileBackListener mOnListener, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(total, progress);
                // LogUtils.d(progress * 100 / total + "---->" + total + "---" + progress);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        String updateUrl2 = "image";
        if (fileType == Constant.IMG_TYPE) {
            updateUrl2 = "image";
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            updateUrl2 = "voice";
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            updateUrl2 = "video";
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetrofitUtils.files2Parts("files", filePaths, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), "PATROL", updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     *
     * @param filePaths   filePaths 文件地址
     * @param fileType    fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     * @param key         key 文件服务器key
     * @param updateUrl1  updateUrl 缺省值
     * @param updateUrl2  updateUrl 缺省值
     */
    public static void updateFiles(String filePaths, int fileType, OnUpdateFileBackListener mOnListener, String key, String updateUrl1, String updateUrl2, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        if (fileType == Constant.IMG_TYPE) {
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetrofitUtils.files2Parts(key, filePaths, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), updateUrl1, updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     *
     * @param files       files 文件
     * @param fileType    fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     */
    public static void updateFiles(File[] files, int fileType, OnUpdateFileBackListener mOnListener, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(total, progress);
                // LogUtils.d(progress * 100 / total + "---->" + total + "---" + progress);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        String updateUrl2 = "image";
        if (fileType == Constant.IMG_TYPE) {
            updateUrl2 = "image";
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            updateUrl2 = "voice";
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            updateUrl2 = "video";
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetrofitUtils.files2Parts("files", files, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), "PATROL", updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     *
     * @param files       files 文件
     * @param fileType    fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     * @param key         key 文件服务器key
     * @param updateUrl1  updateUrl 缺省值
     * @param updateUrl2  updateUrl 缺省值
     */
    public static void updateFiles(File[] files, int fileType, OnUpdateFileBackListener mOnListener, String key, String updateUrl1, String updateUrl2, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        if (fileType == Constant.IMG_TYPE) {
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetrofitUtils.files2Parts(key, files, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), updateUrl1, updateUrl2, part);
        call.enqueue(callback);
    }


    /**
     * http://api.map.baidu.com/geocoder/v2/?output=json&ak=pzOt9fBcS13j90tvaQ12AmMiEZsBwnDd&location=30.649777535000055,104.03086590000004&mcode=F3:1F:F6:C5:58:47:E9:5A:EE:6B:2C:4B:BC:78:B2:EC:D2:94:FF:F1;com.cdqj.cdqjpatrolandroid     * 计划图层数据加载
     *
     * @param point          point
     * @param addressByPoint 监听
     */
    public static void getAddressByPoint(Point point, IAddressByPoint addressByPoint, int flag) {
        // http://api.map.baidu.com/geocoder/v2
        double[] pointNew = CoordinateUtils.wgs84ToBd09(point.getX(), point.getY());
        Map<String, String> map = new HashMap<>(4);
        map.put("location", pointNew[1] + "," + pointNew[0]);
        map.put("output", "json");
        map.put("ak", Constant.BAIDU_AK);
        map.put("coordtype", "wgs84ll");
        new Retrofit.Builder()
                .baseUrl("http://api.map.baidu.com/")
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(PatrolApiService.class)
                .getAddressByPoint(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BdAddressBean>() {
                    @Override
                    public void onResult(BdAddressBean bean) {
                        addressByPoint.onGetAddressByPoint(bean, flag);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        addressByPoint.onFailure(e, flag);
                        e.printStackTrace();
                    }
                });
    }

    public static void getAddressByPoint(Point point, TextView textView) {
        // http://api.map.baidu.com/geocoder/v2/?output=json&ak=pzOt9fBcS13j90tvaQ12AmMiEZsBwnDd&location=30.649777535000055,104.03086590000004&mcode=F3:1F:F6:C5:58:47:E9:5A:EE:6B:2C:4B:BC:78:B2:EC:D2:94:FF:F1;com.cdqj.cdqjpatrolandroid
        if (Constant.appCompany.equals("唐昌")) {//唐昌 是成都坐标需转换
            coorDinateTrans(point.getY() + "," + point.getX(), textView);
        } else {
            getAddByPoint(point, textView);
        }
    }

    private static void getAddByPoint(Point point, TextView textView) {
        double[] pointNew = CoordinateUtils.wgs84ToBd09(point.getY(), point.getX());
        Map<String, String> map = new HashMap<>(4);
        map.put("location", pointNew[1] + "," + pointNew[0]);
        map.put("output", "json");
        //测试
        //map.put("mcode","F3:1F:F6:C5:58:47:E9:5A:EE:6B:2C:4B:BC:78:B2:EC:D2:94:FF:F1;com.cdqj.cdqjpatrolandroid");
        // 正式
        // map.put("mcode", "40:51:7A:63:44:D6:A2:B3:42:CD:28:0B:28:88:2F:71:02:7E:05:EA;com.cdqj.cdqjpatrolandroid");
        map.put("ak", Constant.BAIDU_AK);
        map.put("coordtype", "wgs84ll");
        LogUtils.e("--->" + map.toString());
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        new Retrofit.Builder()
                .baseUrl("http://api.map.baidu.com/")
                .addConverterFactory(CustomGsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(rxAdapter)
                .build()
                .create(PatrolApiService.class)
                .getAddressByPoint(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BdAddressBean>() {
                    @Override
                    public void onResult(BdAddressBean bean) {
                        LogUtils.e("--->" + bean.toString());
                        textView.setText(bean.getResult().getFormatted_address());
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void coorDinateTrans(String pointStr, TextView textView) {
        /*  * @param type    1 cdtk 2 tk2cd
         * @param points  字串格式："经度，纬度"
         * @param method  method
         * @param rs_type rs_type
         * @param js_s    js_s*/
        Map<String, String> map = new HashMap<>();
        map.put("param", Constant.REQUEST_ADDRESS_DEFAULT + "gis/trans.do?");
        map.put("method", "getCD22");
        map.put("points", pointStr);
        map.put("type", "1");
        map.put("rs_type", "3");
        map.put("js_s", PreferencesUtil.getString(Constant.TOKEN));
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        new Retrofit.Builder()
                .baseUrl(Constant.FH_INTERNET_SERVER_ADDRESS_TEST)
                .addConverterFactory(CustomGsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(rxAdapter)
                .build()
                .create(PatrolApiService.class)
                .getCoordinate(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ModelAcoordResultBean>() {
                    @Override
                    public void onResult(ModelAcoordResultBean modelAcoordResultBean) {
                        LogUtils.e("--->" + modelAcoordResultBean.toString());
                        if (!modelAcoordResultBean.getData().isEmpty()) {
                            ModelAcoordResultBean.DataBean dataBean = modelAcoordResultBean.getData().get(0);
                            Point point = new Point(Double.parseDouble(dataBean.getY()), Double.parseDouble(dataBean.getX()));
                            getAddByPoint(point, textView);
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }
                });
    }

    /**
     * 坐标转换
     * @param type 1 cd2tk 2 tk2cd
     */
    public static void coorDinateTrans(String pointStr, String type, ICdToLatlon iCdToLatlon) {
        //pointStr  字串格式："经度，纬度"
        Map<String, String> map = new HashMap<>();
        map.put("param", Constant.REQUEST_ADDRESS_DEFAULT + "gis/trans.do?");
        map.put("method", "getCD22");
        map.put("points", pointStr);
        map.put("type", type);
        map.put("rs_type", "3");
        map.put("js_s", PreferencesUtil.getString(Constant.TOKEN));
//        RetrofitManager.getApiService()
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        new Retrofit.Builder()
                .baseUrl(Constant.FH_INTERNET_SERVER_ADDRESS_TEST)
                .addConverterFactory(CustomGsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(rxAdapter)
                .build()
                .create(PatrolApiService.class)
                .getCoordinate(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<ModelAcoordResultBean>() {
                    @Override
                    public void onResult(ModelAcoordResultBean modelAcoordResultBean) {
                        LogUtils.v(modelAcoordResultBean.toString());
                        if (!modelAcoordResultBean.getData().isEmpty() && iCdToLatlon != null) {
                            List<Point> points = new ArrayList<>();
                            for (ModelAcoordResultBean.DataBean datum : modelAcoordResultBean.getData()) {
                                Point point = new Point(Double.parseDouble(datum.getY()), Double.parseDouble(datum.getX()));
                                points.add(point);
                            }
                            iCdToLatlon.getPoints(points);
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        LogUtils.v(e.toString());
                    }
                });
    }

    /**
     * 坐标转换
     * @param type 1 cd2tk 2 tk2cd
     */
    public static void coorDinateTrans(String pointStr, String type, int flag, ICdToLatlonAndFlag iCdToLatlon) {
        //pointStr  字串格式："经度，纬度"
        Map<String, String> map = new HashMap<>();
        map.put("param", Constant.REQUEST_ADDRESS_DEFAULT + "gis/trans.do?");
        map.put("method", "getCD22");
        map.put("points", pointStr);
        map.put("type", type);
        map.put("rs_type", "3");
        map.put("js_s", PreferencesUtil.getString(Constant.TOKEN));
//        RetrofitManager.getApiService()
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        new Retrofit.Builder()
                .baseUrl(Constant.FH_INTERNET_SERVER_ADDRESS_TEST)
                .addConverterFactory(CustomGsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(rxAdapter)
                .build()
                .create(PatrolApiService.class)
                .getCoordinate(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<ModelAcoordResultBean>() {
                    @Override
                    public void onResult(ModelAcoordResultBean bean) {
                        if (!bean.getData().isEmpty() && iCdToLatlon != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < bean.getData().size(); i++) {
                                stringBuilder.append(bean.getData().get(i).getY()).append(",")
                                        .append(bean.getData().get(i).getX());
                                if (i != bean.getData().size() - 1) {
                                    stringBuilder.append(" ");
                                }
                            }
                            iCdToLatlon.getPoints(stringBuilder.toString(), flag);
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        LogUtils.v(e.toString());
                    }
                });
    }

    /**
     * 执行上下班操作
     *
     * @param mOnListener mOnListener 回调
     * @param status      1 上班 2 下班
     */
    public static void doWork(PatrolOnListener mOnListener, int status) {
        // 下班状态，执行上班
        Map<String, String> map = new HashMap<>(4);
        map.put("status", String.valueOf(status));
        map.put("id", String.valueOf(PreferencesUtil.getInt(Constant.USER_ID)));
        map.put("lat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
        map.put("lon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
        RetrofitManager.getApiService()
                .submitChangeWorkStatus(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onSubmitOffWorkSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                    }
                });
    }

    public static void doOnWork(Context context, int status) {
        new ConfirmSelectDialog(context)
                .setContentStr("您当前处于下班状态，请您先执行上班操作")
                .setYesStr("上班")
                .setNoStr("稍后执行")
                .setListener(new ConfirmDialogListener() {
                    @Override
                    public void onYesClick() {
                        showProgress(context, "");
                        // 下班状态，执行上班
                        Map<String, String> map = new HashMap<>(4);
                        map.put("status", String.valueOf(status));
                        map.put("id", String.valueOf(PreferencesUtil.getInt(Constant.USER_ID)));
                        map.put("lat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
                        map.put("lon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
                        RetrofitManager.getApiService()
                                .submitChangeWorkStatus(map)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                                    @Override
                                    public void onResult(BasePostResponse<Object> bean) {
                                        hideProgress();
                                        if (bean.isSuccess()) {
                                            // 上班执行成功
                                            ToastBuilder.showShort((GlobalConfig.isDoWork ? "下班" : "上班") + "操作成功");
                                            GlobalConfig.isDoWork = !GlobalConfig.isDoWork;
                                            Intent intent = new Intent();
                                            intent.setAction(MyFragment.DO_WORK_STATUS_CHANGE);
                                            context.sendBroadcast(intent);
                                        } else {
                                            ToastBuilder.showShortError(bean.getMsg());
                                        }
                                    }

                                    @Override
                                    public void onError(ExceptionHandle.ResponeThrowable e) {
                                        hideProgress();
                                        ToastBuilder.showShortError(e.message);
                                    }
                                });
                    }

                    @Override
                    public void onNoClick() {

                    }
                }).initView().show();
    }

    /**
     * 查询道路
     *
     * @param mOnListener mOnListener 回调
     * @param keyWord     1 上班 2 下班
     */
    public static void searchRound(SearchRoundOnListener mOnListener, String mapBound, String keyWord, int count) {
        // http://api.tianditu.gov.cn/apiserver/ajaxproxy?proxyReqUrl=http://api.tianditu.gov.cn/search?
        // postStr={'keyWord':'万达广场','level':'18','mapBound':'113.29116,40.07126,113.29682,40.07181','queryType':'1','start':'0','count':'10'}&type=query
        // 大同 'mapBound':'112.80844,39.87019,114.25726,40.38993'
        SearchRoundRequestBean bean = new SearchRoundRequestBean();
        bean.setCount(String.valueOf(count));
        bean.setLevel("18");
        bean.setMapBound(mapBound);
        bean.setKeyWord(keyWord);
        bean.setQueryType("1");
        bean.setStart("0");
        Map<String, String> map = new HashMap<>(1);
        map.put("proxyReqUrl", "http://api.tianditu.gov.cn/search?postStr=" + GsonUtils.gsonBuilder.create().toJson(bean).replace("\"", "\'"));
        //map.put("type", "query");
        map.put("tk", Constant.TDT_KEY);
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        new Retrofit.Builder()
                .baseUrl("http://api.tianditu.gov.cn/")
                .addConverterFactory(new ToStringConverterFactory())
                .addCallAdapterFactory(rxAdapter)
                .build()
                .create(PatrolApiService.class)
                .searchRound(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onResult(String bean) {
                        mOnListener.onSearchRoundSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        LogUtils.d(e.message);
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 查询道路
     *
     * @param mOnListener mOnListener 回调
     * @param bean        查询请求对象
     */
    public static void searchRound(SearchRoundOnListener mOnListener, SearchRoundRequestBean bean) {
        // http://api.tianditu.gov.cn/apiserver/ajaxproxy?proxyReqUrl=http://api.tianditu.gov.cn/search?
        // postStr={'keyWord':'万达广场','level':'18','mapBound':'113.29116,40.07126,113.29682,40.07181','queryType':'1','start':'0','count':'10'}&type=query
        Map<String, String> map = new HashMap<>(2);
        map.put("proxyReqUrl", "http://api.tianditu.gov.cn/search?postStr=" + GsonUtils.gsonBuilder.create().toJson(bean).replace("\"", "\'"));
        map.put("tk", Constant.TDT_KEY);
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        new Retrofit.Builder()
                .baseUrl("http://api.tianditu.gov.cn/")
                .addConverterFactory(CustomGsonConverterFactory.create(GsonUtils.gsonBuilder.create()))
                .addCallAdapterFactory(rxAdapter)
                .build()
                .create(PatrolApiService.class)
                .searchRound(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onResult(String bean) {
                        mOnListener.onSearchRoundSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        LogUtils.d(e.message);
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 请求地图参数
     *
     * @param mOnListener mOnListener 回调
     * @param context     context
     */
    public static void getGisParam(Context context, IGetGisParameterListener mOnListener) {
        // http://172.16.102.227:12080/gis/config/mobilemap
        showProgress(context, "获取地图参数中");
        RetrofitManager.getApiService()
                .getGisParam()
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<GisParamsBean>() {
                    @Override
                    public void onResult(GisParamsBean bean) {
                        hideProgress();
                        mOnListener.onGetGisParam(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        hideProgress();
                        LogUtils.d(e.message);
                        e.printStackTrace();
                    }
                });
    }

    public static void showProgress(Context context, String str) {
        progressDialog = LoadingHelper.startProgressDialog(context, progressDialog, str);
    }

    public static void hideProgress() {
        progressDialog = LoadingHelper.stopProgressDialog(progressDialog);
    }

    /**
     * 执行上下班操作-回调接口
     */
    public interface PatrolOnListener {
        /**
         * 网络请求失败
         *
         * @param e e
         */
        void onFailure(ExceptionHandle.ResponeThrowable e);

        /**
         * 执行上班成功
         *
         * @param body body
         */
        void onSubmitOffWorkSuccess(BasePostResponse<Object> body);
    }

    /**
     * 查询道路-回调接口
     */
    public interface SearchRoundOnListener {
        /**
         * 查询道路成功
         *
         * @param body body
         */
        void onSearchRoundSuccess(String body);
    }

    /**
     * 坐标转换回调
     */
    public interface ICdToLatlonAndFlag {
        void getPoints(String points,int flag);
    }

}
