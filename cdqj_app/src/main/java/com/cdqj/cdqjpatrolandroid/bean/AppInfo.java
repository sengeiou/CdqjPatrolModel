package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2019/5/14
 * 创建时间： 9:06
 * 描述：app信息
 */
public class AppInfo extends RealmObject implements Parcelable {
    /**
     * 包名、app名字、地址、版本名字、版本code、是否是系统app
     */
    private String   packageName;
    private String   name;
    private String   packagePath;
    private String   versionName;
    private int      versionCode;
    private boolean  isSystem;

    public AppInfo(String packageName, String name, String packagePath, String versionName, int versionCode, boolean isSystem) {
        this.packageName = packageName;
        this.name = name;
        this.packagePath = packagePath;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.isSystem = isSystem;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.name);
        dest.writeString(this.packagePath);
        dest.writeString(this.versionName);
        dest.writeInt(this.versionCode);
        dest.writeByte(this.isSystem ? (byte) 1 : (byte) 0);
    }

    public AppInfo() {
    }

    protected AppInfo(Parcel in) {
        this.packageName = in.readString();
        this.name = in.readString();
        this.packagePath = in.readString();
        this.versionName = in.readString();
        this.versionCode = in.readInt();
        this.isSystem = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };

    @Override
    public String toString() {
        return "AppInfo{" +
                "packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                ", packagePath='" + packagePath + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", isSystem=" + isSystem +
                '}';
    }
}
