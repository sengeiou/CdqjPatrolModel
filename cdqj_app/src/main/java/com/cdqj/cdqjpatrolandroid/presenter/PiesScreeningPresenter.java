package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.view.i.IPiesScreenView;

import java.util.ArrayList;


public class PiesScreeningPresenter extends BasePresenter<IPiesScreenView> {

    public PiesScreeningPresenter(IPiesScreenView mView) {
        super(mView);
    }

    public void getGroupUser(String path) {
        mView.showProgress();
        addSubscription(mApiService.groupUser(path), new BaseSubscriber<ArrayList<UserCom>>() {
                    @Override
                    public void onResult(ArrayList<UserCom> userComs) {
                        mView.hideProgress();
                        mView.getGroupUser(userComs);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mView.hideProgress();
                        mView.onFailure(e);
                        e.printStackTrace();
                    }
                });
    }
}
