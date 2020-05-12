package com.cdqj.cdqjpatrolandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.image.show.CustomImageShowActivity;
import com.cdqj.cdqjpatrolandroid.image.show.ImageViewBean;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.previewlibrary.GPreviewBuilder;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lyf on 2018/9/18 15:22
 *
 * @author lyf
 * desc：图片处理
 */
public class PictureProcessingUtil {

    /**
     * Return the bitmap with text watermarking.
     *
     * @param bitmap      The source of bitmap.
     * @return the bitmap with text watermarking
     */
    public static Bitmap drawTextLocation(Bitmap bitmap) {
        if (ObjectUtils.isEmpty(bitmap)) {
            return null;
        }
        // 压缩重绘
        Bitmap newBitmap = ImageUtils.compressBySampleSize(bitmap, Constant.IMAGE_MAX_SAMPLE_SIZE);
        newBitmap = ImageUtils.compressByQuality(newBitmap, Constant.IMAGE_MAX_SIZE);
        newBitmap = ImageUtils.addTextWatermark(newBitmap, PreferencesUtil.getString(Constant.USER_NAME),
                ConvertUtils.sp2px(15), ContextCompat.getColor(App.getInstance(), R.color.green), ConvertUtils.dp2px(20), ConvertUtils.dp2px(20));
        newBitmap = ImageUtils.addTextWatermark(newBitmap, PreferencesUtil.getString(Constant.TRUE_NAME),
                ConvertUtils.sp2px(15), ContextCompat.getColor(App.getInstance(), R.color.green), ConvertUtils.dp2px(20), ConvertUtils.dp2px(20 + 20));
        newBitmap = ImageUtils.addTextWatermark(newBitmap, PreferencesUtil.getString(Constant.LOCATION_LATITUDE) + "," + PreferencesUtil.getString(Constant.LOCATION_LONGITUDE),
                ConvertUtils.sp2px(15), ContextCompat.getColor(App.getInstance(), R.color.green), ConvertUtils.dp2px(20), ConvertUtils.dp2px(20 + 20*2));
        newBitmap = ImageUtils.addTextWatermark(newBitmap, PreferencesUtil.getString(Constant.LOCATION_ADDRESS),
                ConvertUtils.sp2px(15), ContextCompat.getColor(App.getInstance(), R.color.green), ConvertUtils.dp2px(20), ConvertUtils.dp2px(20 + 20*3));
        return newBitmap;
    }

    /**
     * Return the bitmap with text watermarking.
     *
     * @param bitmapPath      bitmapPath
     * @return the bitmap with text watermarking
     */
    public static Bitmap drawTextLocation(String bitmapPath) {
        Bitmap bitmap = drawTextLocation(getBitmapFromPath(bitmapPath));
        // 绘制后删除原图
        FileUtils.deleteFile(bitmapPath);
        return bitmap;
    }

    /**
     * 保存图片
     *
     * @param bitmap      The source of bitmap.
     * @return 是否保存成功
     */
    public static boolean saveImgLocation(Bitmap bitmap) {
        if (FileUtils.createOrExistsDir(Constant.APP_IMAGE_PATH)) {
            File outDir =  FileUtils.getFileByPath(Constant.APP_IMAGE_PATH + File.separator + System.currentTimeMillis() + ".jpg");
            return saveImgLocation(bitmap, outDir);
        }
        return false;
    }

    /**
     * 保存图片
     *
     * @param bitmap      The source of bitmap.
     * @param outDir      outDir 文件夹+文件名
     * @return 是否保存成功
     */
    public static boolean saveImgLocation(Bitmap bitmap, File outDir) {
        return ImageUtils.save(bitmap, outDir, Bitmap.CompressFormat.JPEG);
    }

    /**
     * 保存图片
     *
     * @param bitmap      The source of bitmap.
     * @param outDirPath      outDirPath 文件夹
     * @param name      name 文件名
     * @return 是否保存成功
     */
    public static boolean saveImgLocation(Bitmap bitmap, String outDirPath, String name) {
        File outDir =  FileUtils.getFileByPath(outDirPath + File.separator + name);
        return saveImgLocation(bitmap, outDir);
    }

    /**
     * 开始拍照(Activity)
     * @param context Activity
     * @return  pathImage pathImage
     */
    public static String takePicture(Activity context) {
        String pathImage = "";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            if (FileUtils.createOrExistsDir(Constant.APP_PATH)) {
                File outDir =  FileUtils.getFileByPath(Constant.APP_PATH);
                File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
                pathImage = outFile.getAbsolutePath();
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(App.getInstance(), AppUtils.getAppPackageName() + ".provider", outFile);
                } else {
                    uri = Uri.fromFile(outFile);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                context.startActivityForResult(intent, Constant.REQUEST_CODE_IMAGE_CAPTURE);
            }
        }
        return pathImage;
    }

    /**
     * 开始拍照(Activity)
     * @param context Activity
     * @param requestCode requestCode
     * @return  pathImage pathImage
     */
    public static String takePicture(Activity context, int requestCode) {
        String pathImage = "";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            if (FileUtils.createOrExistsDir(Constant.APP_PATH)) {
                File outDir =  FileUtils.getFileByPath(Constant.APP_PATH);
                File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
                pathImage = outFile.getAbsolutePath();
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(App.getInstance(), AppUtils.getAppPackageName() + ".provider", outFile);
                } else {
                    uri = Uri.fromFile(outFile);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                context.startActivityForResult(intent, requestCode);
            }
        }
        return pathImage;
    }

    /**
     * 开始拍照(Fragment)
     * @param context Fragment
     * @return  pathImage pathImage
     */
    public static String takePicture(Fragment context) {
        String pathImage = "";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            if (FileUtils.createOrExistsDir(Constant.APP_PATH)) {
                File outDir =  FileUtils.getFileByPath(Constant.APP_PATH);
                File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
                pathImage = outFile.getAbsolutePath();
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(App.getInstance(), AppUtils.getAppPackageName() + ".provider", outFile);
                } else {
                    uri = Uri.fromFile(outFile);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                context.startActivityForResult(intent, Constant.REQUEST_CODE_IMAGE_CAPTURE);
            }
        }
        return pathImage;
    }

    /**
     * 照片选择、默认相册(Fragment)
     * @param context Fragment
     */
    public static void checkPicture(Fragment context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        context.startActivityForResult(Intent.createChooser(intent, "选择图片"),
                Constant.REQUEST_CODE_IMAGE_CHECK);
    }

    /**
     * 照片选择、默认相册(Activity)
     * @param context Fragment
     */
    public static void checkPicture(Activity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        context.startActivityForResult(Intent.createChooser(intent, "选择图片"),
                Constant.REQUEST_CODE_IMAGE_CHECK);
    }

    /**
     * 开始拍照(Fragment)
     * @param context Fragment
     * @param requestCode requestCode
     * @return  pathImage pathImage
     */
    public static String takePicture(Fragment context, int requestCode) {
        String pathImage = "";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            if (FileUtils.createOrExistsDir(Constant.APP_PATH)) {
                File outDir =  FileUtils.getFileByPath(Constant.APP_PATH);
                File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
                pathImage = outFile.getAbsolutePath();
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(App.getInstance(), AppUtils.getAppPackageName() + ".provider", outFile);
                } else {
                    uri = Uri.fromFile(outFile);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                context.startActivityForResult(intent, requestCode);
            }
        }
        return pathImage;
    }

    /**
     * 根据路径获取图片
     * @param path path
     * @return Bitmap
     */
    public static Bitmap getBitmapFromPath(String path) {
        if (!FileUtils.isFileExists(new File(path))) {
            return null;
        }
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 图片查看
     * @param mContext mContext
     * @param imgPath imgPath
     * @param thumbView thumbView
     */
    public static void imgSingeShow(Context mContext, String imgPath, ImageView thumbView) {
        ArrayList<ImageViewBean> mThumbViewInfoList = new ArrayList<>();
        mThumbViewInfoList.add(new ImageViewBean(StringUrlUtil.transHttpUrlAndOnlyOne(imgPath)));
        computeBoundsBackward(mThumbViewInfoList, thumbView);
        GPreviewBuilder.from((Activity) mContext)
                .to(CustomImageShowActivity.class)
                .setData(mThumbViewInfoList)
                .setCurrentIndex(0)
                //是否在黑屏区域点击返回
                .setSingleFling(false)
                //是否禁用图片拖拽返回
                .setDrag(false)
                .setType(GPreviewBuilder.IndicatorType.Dot)
                .start();
    }

    /**
     * 图片查看
     * @param mContext mContext
     * @param imgPath imgPath
     */
    public static void imgSingeShow(Context mContext, String imgPath) {
        ImageView thumbView = new ImageView(mContext);
        imgSingeShow(mContext, imgPath, thumbView);
    }

    /**
     * 图片查看多张
     * @param mContext mContext
     * @param imgPath imgPath
     * @param thumbView thumbView
     */
    public static void imgMoreSHow(Context mContext, String imgPath,ImageView thumbView) {
        ArrayList<ImageViewBean> mThumbViewInfoList = new ArrayList<>();
        String[] imags = imgPath.split(",");
        for (String img : imags) {
            if (!StringUtils.isTrimEmpty(img)) {
                mThumbViewInfoList.add(new ImageViewBean(StringUrlUtil.transHttpUrlAndOnlyOne(img)));
            }
        }
        computeBoundsBackward(mThumbViewInfoList, thumbView);
        GPreviewBuilder.from((Activity) mContext)
                .to(CustomImageShowActivity.class)
                .setData(mThumbViewInfoList)
                .setCurrentIndex(0)
                //是否在黑屏区域点击返回
                .setSingleFling(false)
                //是否禁用图片拖拽返回
                .setDrag(false)
                .setType(GPreviewBuilder.IndicatorType.Dot)
                .start();
    }

    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     * @param thumbView 需要显示过度控件
     * @param mThumbViewInfoList mThumbViewInfoList
     */
    private static void computeBoundsBackward(ArrayList<ImageViewBean> mThumbViewInfoList,ImageView thumbView) {
        Rect bounds = new Rect();
        if (thumbView != null) {
            //拿到在控件屏幕可见中控件显示为矩形Rect信息
            thumbView.getGlobalVisibleRect(bounds);
        }
        mThumbViewInfoList.get(0).setBounds(bounds);
    }
}
