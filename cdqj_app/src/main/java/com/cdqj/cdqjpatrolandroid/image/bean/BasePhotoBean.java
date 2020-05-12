package com.cdqj.cdqjpatrolandroid.image.bean;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2020/1/11
 * 创建时间： 13:38
 * 描述：拍照显示控件及存放的地址对象
 */
public class BasePhotoBean {

    /**
     * 控件
     */
    private RecyclerView view;
    /**
     * 照片
     */
    private ArrayList<LocalMedia> photoList;
    /**
     * 是否只是查看
     */
    private boolean isExamine = false;
    /**
     * 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     */
    private int chooseMode = PictureMimeType.ofAll();
    /**
     * 最大照片数量
     */
    private int mixCount = Constant.PICTURE_MAX_COUNT;

    public BasePhotoBean(@Nullable RecyclerView view, @Nullable ArrayList<LocalMedia> photoList) {
        this.view = view;
        this.photoList = photoList;
    }

    public BasePhotoBean(@Nullable RecyclerView view, @Nullable ArrayList<LocalMedia> photoList, int mixCount) {
        this.view = view;
        this.photoList = photoList;
        this.mixCount = mixCount;
    }

    public BasePhotoBean(RecyclerView view, ArrayList<LocalMedia> photoList, int chooseMode, int mixCount) {
        this.view = view;
        this.photoList = photoList;
        this.chooseMode = chooseMode;
        this.mixCount = mixCount;
    }

    public RecyclerView getView() {
        return view;
    }

    public void setView(RecyclerView view) {
        this.view = view;
    }

    public ArrayList<LocalMedia> getPhotoList() {
        return photoList == null ? new ArrayList<>() : photoList;
    }

    public void setPhotoList(ArrayList<LocalMedia> photoList) {
        this.photoList = photoList;
    }

    public boolean isExamine() {
        return isExamine;
    }

    public void setExamine(boolean examine) {
        isExamine = examine;
    }

    public int getChooseMode() {
        return chooseMode;
    }

    public void setChooseMode(int chooseMode) {
        this.chooseMode = chooseMode;
    }

    public int getMixCount() {
        return mixCount;
    }

    public void setMixCount(int mixCount) {
        this.mixCount = mixCount;
    }
}
