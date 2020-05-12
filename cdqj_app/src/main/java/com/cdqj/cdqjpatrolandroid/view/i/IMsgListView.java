package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.MsgBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：信息列表
 */
public interface IMsgListView extends BaseView{
    /**
     * 获取信息列表结果
     * @param body body
     * @param flag flag
     */
    void onGetMsgListSuccess(BaseGetResponse<MsgBean> body, boolean flag);

    /**
     * 消息状态更改成功
     * @param body body
     */
    void onAddUpdateMsgSuccess(BasePostResponse<Object> body);
}
