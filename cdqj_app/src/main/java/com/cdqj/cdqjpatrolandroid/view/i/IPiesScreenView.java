package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.ArrayList;

/**
 * Created by lyf on 2018/9/3 14:43
 *
 * @author lyf
 * desc：意见反馈
 */
public interface IPiesScreenView extends BaseView {
    /**
     * 查询分组权用户有树
     * @param userComs
     */
    void getGroupUser(ArrayList<UserCom> userComs);
}
