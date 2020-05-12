package com.cdqj.cdqjpatrolandroid.gis.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;

/**
 * Created by lyf on 2018/9/13 10:18
 *
 * @author lyf
 * desc：绘制|图形展示工具类
 */
public class SymobelUtils {

    /**
     * View 创建 PictureMarkerSymbol
     * @param context context
     * @param layoutID layoutID
     * @return PictureMarkerSymbol
     */
    public static PictureMarkerSymbol pictureSymobel(Context context, int layoutID) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vw = inflater.inflate(layoutID, null);

        return pictureSymobel(context, vw);
    }

    /**
     * 创建 PictureMarkerSymbol
     * @param context context
     * @param resId resId
     * @return PictureMarkerSymbol
     */
    public static PictureMarkerSymbol pictureSymobelByResId(Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        assert drawable != null;
        return new PictureMarkerSymbol((BitmapDrawable)drawable);
    }

    /**
     * View 创建 PictureMarkerSymbol
     * @param context context
     * @param view view
     * @return PictureMarkerSymbol
     */
    public static PictureMarkerSymbol pictureSymobel(Context context, View view) {
        Bitmap bt = ImageUtils.view2Bitmap(view);
        BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

        return new PictureMarkerSymbol(temp);
    }

    /**
     * 创建 PictureMarkerSymbol
     * @param context context
     * @param label 字符串
     * @param drawableResId Drawable资源ID
     * @return PictureMarkerSymbol
     */
    @SuppressLint("WrongConstant")
    public static PictureMarkerSymbol textPicSymobel(Context context, CharSequence label, int color, float size, int drawableResId, MODE mode) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));

        ImageView imgView = new ImageView(context);
        imgView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        Drawable db = ContextCompat.getDrawable(context, drawableResId);
        imgView.setImageDrawable(db);

        TextView txtView = new TextView(context);
        txtView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        txtView.setText(label);
        txtView.setTextColor(color);
        txtView.setTextSize(size);

        switch (mode) {
            case BOTTOM:
                layout.addView(txtView);
                layout.addView(imgView);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                break;
            case LEFT:
                layout.addView(imgView);
                layout.addView(txtView);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                break;
            case RIGHT:
                layout.addView(txtView);
                layout.addView(imgView);
                layout.setOrientation(LinearLayout.VERTICAL);
                break;
            case TOP:
                layout.addView(imgView);
                layout.addView(txtView);
                layout.setOrientation(LinearLayout.VERTICAL);
            default:
                break;
        }
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, 0),
                View.MeasureSpec.makeMeasureSpec(0, 0));
        layout.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight());
        Bitmap bt = ImageUtils.view2Bitmap(layout);
        BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

        return new PictureMarkerSymbol(temp);
    }

    /**
     * 创建 PictureMarkerSymbol
     * @param context context
     * @param label 字符串
     * @param color color
     * @param size size
     * @return PictureMarkerSymbol
     */
    @SuppressLint("WrongConstant")
    public static PictureMarkerSymbol textPicSymobel(Context context, CharSequence label, int color, float size) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));

        TextView txtView = new TextView(context);
        txtView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        txtView.setText(label);
        txtView.setTextColor(color);
        txtView.setTextSize(size);

        layout.addView(txtView);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, 0),
                View.MeasureSpec.makeMeasureSpec(0, 0));
        layout.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight());
        // View转换为bitmap
        Bitmap bt = ImageUtils.view2Bitmap(layout);
        BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

        return new PictureMarkerSymbol(temp);
    }

    public enum MODE {
        /**
         * 右
         */
        RIGHT,
        LEFT,
        TOP,
        BOTTOM
    }


    public static SimpleFillSymbol getSimpleFillSymbol(int red, int green, int blue) {
        SimpleFillSymbol sfs = new SimpleFillSymbol();
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol();
        simpleLineSymbol.setColor(Color.BLUE);
        simpleLineSymbol.setWidth(ConvertUtils.dp2px(1.5F));
        sfs.setOutline(simpleLineSymbol);
        sfs.setColor(Color.argb(50,red,green,blue));
        return sfs;
    }

    public static SimpleFillSymbol getSimpleFillSymbol(int colorId) {
        SimpleFillSymbol sfs = new SimpleFillSymbol();
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol();
        simpleLineSymbol.setColor(Color.BLUE);
        simpleLineSymbol.setWidth(ConvertUtils.dp2px(1.5F));
        sfs.setOutline(simpleLineSymbol);
        sfs.setColor(colorId);
        return sfs;
    }

    public static SimpleFillSymbol getSimpleFillSymbol(Context context, String colorStr) {
        SimpleFillSymbol sfs = new SimpleFillSymbol();
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol();
        simpleLineSymbol.setColor(Color.parseColor(colorStr));
        simpleLineSymbol.setWidth(ConvertUtils.dp2px(1.5F));
        sfs.setOutline(simpleLineSymbol);
        // 透明背景图
        sfs.setColor(ContextCompat.getColor(context, R.color.transparent));
        return sfs;
    }

    public static SimpleFillSymbol getSimpleFillSymbol(Context context, String colorStr, String bgColorStr) {
        SimpleFillSymbol sfs = new SimpleFillSymbol();
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol();
        simpleLineSymbol.setColor(Color.parseColor(colorStr));
        simpleLineSymbol.setWidth(ConvertUtils.dp2px(1.5F));
        sfs.setOutline(simpleLineSymbol);
        sfs.setColor(Color.parseColor(bgColorStr));
        return sfs;
    }

    /**
     * 绘制画笔
     * @param context context
     * @param colorStr 颜色
     * @param size 宽度大小
     * @return SimpleFillSymbol
     */
    public static SimpleFillSymbol getSimpleFillSymbol(Context context, String colorStr, int size) {
        SimpleFillSymbol sfs = new SimpleFillSymbol();
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol();
        simpleLineSymbol.setColor(Color.parseColor(colorStr));
        simpleLineSymbol.setWidth(ConvertUtils.dp2px(size));
        sfs.setOutline(simpleLineSymbol);
        // 透明背景图
        sfs.setColor(ContextCompat.getColor(context, R.color.transparent));
        return sfs;
    }

    /**
     * 异步创建PictureMarkerSymbol
     * @param temp 显示图片|面等
     */
    public void createAsyncPictureMarkerSymbol(BitmapDrawable temp) {
        ListenableFuture<PictureMarkerSymbol> pictureLis = PictureMarkerSymbol.createAsync(temp);
        /*pictureLis.addDoneListener(() -> {
            try {
                // 回调返回PictureMarkerSymbol
                pictureLis.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });*/
    }
}
