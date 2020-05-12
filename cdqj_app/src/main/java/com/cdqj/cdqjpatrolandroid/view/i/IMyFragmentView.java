package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.MyFragmentTaskNumberBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：我的界面
 */
public interface IMyFragmentView extends BaseView {

    /**
     * 头像上传成功
     * @param basePostResponse basePostResponse
     */
    void onUpdateImgSuccess(BasePostResponse<List<UpImgResultBean>> basePostResponse);

    /**
     * 头像地址上传成功
     * @param basePostResponse basePostResponse
     */
    void onUpdateImgToSuccess(BasePostResponse<Object> basePostResponse);

    /**
     * 上下班操作结果
     * @param basePostResponse basePostResponse
     */
    void onChangeWorkStatusSuccess(BasePostResponse<Object> basePostResponse);
    /**
     * 获取任务数量结果
     * @param basePostResponse basePostResponse
     */
    void onGetTaskNumberSuccess(BasePostResponse<MyFragmentTaskNumberBean> basePostResponse);

    void onFailure(String s);

}
