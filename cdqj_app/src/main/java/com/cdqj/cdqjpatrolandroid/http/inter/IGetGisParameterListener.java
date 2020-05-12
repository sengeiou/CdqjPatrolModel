package com.cdqj.cdqjpatrolandroid.http.inter;

import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/10/24 10:19
 *
 * @author lyf
 * desc：请求地图参数回调接口
 */
public interface IGetGisParameterListener {

    /**
     * 获取失败
     * @param e e
     */
    void onFailure(ExceptionHandle.ResponeThrowable e);

    /**
     * 获取成功
     * @param bean bean
     */
    void onGetGisParam(GisParamsBean bean);

}
