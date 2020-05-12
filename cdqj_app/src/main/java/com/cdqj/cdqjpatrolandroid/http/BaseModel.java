package com.cdqj.cdqjpatrolandroid.http;


/**
 * 业务对象的基类
 * @author lyf
 * @date 2018年9月3日 11:41:17
 */
public class BaseModel {

    /**
     * retrofit请求数据的管理类
     */
    public RetrofitManager retrofitManager;

    public BaseModel() {
        retrofitManager = RetrofitManager.getInstance();
    }

}
