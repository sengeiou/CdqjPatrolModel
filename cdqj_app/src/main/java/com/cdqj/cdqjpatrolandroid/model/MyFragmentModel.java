package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.MyFragmentTaskNumberBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：我的界面M
 */
public class MyFragmentModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    private OnUpdateFileBackListener mOnUpdateFileBackListener;

    public MyFragmentModel(PatrolOnListener mOnListener, OnUpdateFileBackListener mOnUpdateFileBackListener) {
        this.mOnListener = mOnListener;
        this.mOnUpdateFileBackListener = mOnUpdateFileBackListener;
    }

    /**
     * 文件上传（照片）
     *
     * @param filePath filePath
     * @param fileType fileType
     */
    public void updateImg(String filePath, int fileType) {
        // file/sysFileInfo/system/userInfo/upload
        RetrofitUtils.updateFile(filePath, fileType, mOnUpdateFileBackListener, 0);
    }

    /**
     * 头像地址上传（照片）
     *
     */
    public BaseSubscriber updateImgTo() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onUpdateImgToSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };

    }

    /**
     * 获取任务数量
     *
     */
    public BaseSubscriber getTaskNumber() {
        return new BaseSubscriber<BasePostResponse<MyFragmentTaskNumberBean>>() {
                    @Override
                    public void onResult(BasePostResponse<MyFragmentTaskNumberBean> bean) {
                        mOnListener.onGetTaskNumberSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };

    }

    /**
     * 上下班操作
     *
     * @return s
     */
    public BaseSubscriber submitChangeWorkStatus() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onChangeWorkStatusSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };

    }

    /**
     * 回调接口
     */
    public interface PatrolOnListener {
        /**
         * 网络请求失败
         *
         * @param e e
         */
        void onFailure(String e);

        /**
         * 网络请求失败
         *
         * @param e e
         */
        void onFailure(ExceptionHandle.ResponeThrowable e);

        /**
         * 头像地址上传结果
         *
         * @param body body
         */
        void onUpdateImgToSuccess(BasePostResponse<Object> body);

        /**
         * 上下班操作结果
         *
         * @param body body
         */
        void onChangeWorkStatusSuccess(BasePostResponse<Object> body);

        /**
         * 获取任务数量结果
         *
         * @param body body
         */
        void onGetTaskNumberSuccess(BasePostResponse<MyFragmentTaskNumberBean> body);
    }
}
