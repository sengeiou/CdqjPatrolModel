package com.cdqj.cdqjpatrolandroid.image.show;

import android.graphics.Rect;
import android.os.Parcel;

import com.previewlibrary.enitity.IThumbViewInfo;

/**
 * Created by lyf on 2018/5/24 10:27
 *
 * @author lyf
 * desc：
 */
public class ImageViewBean implements IThumbViewInfo {
    /**
     * 图片地址
     */
    private String url;
    /**
     * 记录坐标
     */
    private Rect mBounds;
    private String user;
    /**
     * //视频链接 //不为空是视频
     */
    private String videoUrl;

    public ImageViewBean(String url) {
        this.url = url;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getUser(){
        return user;
    }

    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public String getUrl() {
        //将图片地址字段返回
        return url;
    }

    @Override
    public Rect getBounds() {
        //将图片显示坐标字段返回
        return mBounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, flags);
        dest.writeString(this.user);
        dest.writeString(this.videoUrl);
    }

    protected ImageViewBean(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
        this.user = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Creator<ImageViewBean> CREATOR = new Creator<ImageViewBean>() {
        @Override
        public ImageViewBean createFromParcel(Parcel source) {
            return new ImageViewBean(source);
        }

        @Override
        public ImageViewBean[] newArray(int size) {
            return new ImageViewBean[size];
        }
    };
}
