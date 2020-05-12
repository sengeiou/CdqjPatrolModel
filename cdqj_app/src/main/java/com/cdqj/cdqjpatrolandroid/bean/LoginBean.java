package com.cdqj.cdqjpatrolandroid.bean;

/**
 * Created by lyf on 2018/9/3 16:54
 *
 * @author lyf
 * desc：
 */
public class LoginBean {

    /**
     * 登陆用户名
     */
    private String username;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 是否是手机
     */
    private Boolean isMobile = true;
    /**
     * 手机唯一标识
     */
    private String mobileId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getMobile() {
        return isMobile;
    }

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }
}
