package com.cdqj.cdqjpatrolandroid.http.inter;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.model.BdAddressBean;

/**
 * Created by lyf on 2018/10/24 10:19
 *
 * @author lyf
 * desc：通过经纬度偶去地址|通过地址获取经纬度
 */
public interface IAddressByPoint {
    /**
     * 获取失败
     * @param e e
     * @param flag flag
     */
    void onFailure(ExceptionHandle.ResponeThrowable e, int flag);
    /**
     * 获取成功
     * @param bean bean
     * @param flag flag
     */
    void onGetAddressByPoint(BdAddressBean bean, int flag);

}
