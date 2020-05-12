package com.cdqj.cdqjpatrolandroid.view.ui.my;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.zxing.ZxingUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;

/**
 * 版本分享
 *
 * @author lyf
 */
public class AppShareActivity extends BaseActivity {

    ImageView appShareImg;
    TextView appShareCode;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_app_share;
    }

    @Override
    protected void findView() {
        appShareImg = findViewById(R.id.app_share_img);
        appShareCode = findViewById(R.id.app_share_code);
    }

    @Override
    protected String getTitleText() {
        return "版本分享";
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        appShareCode.setText("当前版本号：V " + PreferencesUtil.getString(Constant.APP_VERSION));
        String path = PreferencesUtil.getString(Constant.FILE_SERVICE_PATH) + getIntent().getStringExtra("appPath");
        // TODO 生成二维码
        Bitmap logoBitmap = ImageUtils.getBitmap(R.mipmap.ic_launcher_round);
        Bitmap bitmap = ZxingUtils.createQRCodeBitmap(path, 500, 500, "UTF-8",
                "H", "1", ContextCompat.getColor(this, R.color.theme_one), Color.WHITE, logoBitmap, 0.3F);
        appShareImg.setImageBitmap(bitmap);
    }
}
