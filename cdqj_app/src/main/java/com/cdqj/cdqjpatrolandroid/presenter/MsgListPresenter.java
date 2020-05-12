package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.MsgBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.MsgListModel;
import com.cdqj.cdqjpatrolandroid.view.i.IMsgListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：信息列表P
 */
public class MsgListPresenter extends BasePresenter<IMsgListView>  implements MsgListModel.PatrolOnListener{

    private MsgListModel mModel;

    public MsgListPresenter(IMsgListView view) {
        super(view);
        mModel = new MsgListModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 信息列表获取结果
     * @param body body
     */
    @Override
    public void onGetMsgListSuccess(BaseGetResponse<MsgBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetMsgListSuccess(body, flag);
    }

    @Override
    public void onAddUpdateMsgSuccess(BasePostResponse<Object> body) {
        mView.onAddUpdateMsgSuccess(body);
    }

    /**
     * 信息列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     */
    public void getMsgList(int page, int pageSize, boolean flag) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolMsgInfo/page 分页查询
        Map<String, Integer> map = new HashMap<>(2);
        map.put("page", page);
        map.put("rows", pageSize);
        addSubscription(mApiService.getMsgList(map), mModel.getMsgList(flag));
    }

    /**
     * 消息状态更改执行
     * @param id id
     * @param status 消息状态 1已读 2未读 3删除
     */
    public void addUpdateMsg(int id, int status) {
        mView.showProgress();
        // patrol/patrolMsgRead/addupdate 添加修改数据
        Map<String, Integer> map = new HashMap<>(2);
        map.put("msgId", id);
        map.put("status", status);
        addSubscription(mApiService.addUpdateMsg(map), mModel.addUpdateMsg());
    }
}
