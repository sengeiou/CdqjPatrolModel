package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyf on 2018/9/3 18:55
 *
 * @author lyf
 * desc：TOKEN中解析出的人员
 */
public class UsernameBean implements Parcelable {

    /**
     * departName : 巡线队
     * deptId : 861
     * domainId : 2
     * empId : 34052951
     * id : 34052952
     * isStart : 1
     * password : b33b59de93a7b31a1b919d2286a075cc
     * trueName : 巡线队长1
     * username : leder1
     */

    private String departName;
    private int deptId;
    private int domainId;
    private int empId;
    private int id;
    private int isStart;
    private String password;
    private String trueName;
    private String username;
    /**
     * 岗位/职位
     */
    private String post;
    /**
     * 头像
     */
    private String photo;
    /**
     * 上下班状态
     */
    private int status;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsStart() {
        return isStart;
    }

    public void setIsStart(int isStart) {
        this.isStart = isStart;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.departName);
        dest.writeInt(this.deptId);
        dest.writeInt(this.domainId);
        dest.writeInt(this.empId);
        dest.writeInt(this.id);
        dest.writeInt(this.isStart);
        dest.writeString(this.password);
        dest.writeString(this.trueName);
        dest.writeString(this.username);
        dest.writeString(this.post);
        dest.writeString(this.photo);
        dest.writeInt(this.status);
    }

    public UsernameBean() {
    }

    protected UsernameBean(Parcel in) {
        this.departName = in.readString();
        this.deptId = in.readInt();
        this.domainId = in.readInt();
        this.empId = in.readInt();
        this.id = in.readInt();
        this.isStart = in.readInt();
        this.password = in.readString();
        this.trueName = in.readString();
        this.username = in.readString();
        this.post = in.readString();
        this.photo = in.readString();
        this.status = in.readInt();
    }

    public static final Creator<UsernameBean> CREATOR = new Creator<UsernameBean>() {
        @Override
        public UsernameBean createFromParcel(Parcel source) {
            return new UsernameBean(source);
        }

        @Override
        public UsernameBean[] newArray(int size) {
            return new UsernameBean[size];
        }
    };
}
