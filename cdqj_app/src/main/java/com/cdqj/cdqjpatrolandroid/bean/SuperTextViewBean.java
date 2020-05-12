package com.cdqj.cdqjpatrolandroid.bean;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2019/9/12
 * 创建时间： 10:08
 * 描述：SuperTextView 对象，适用于列表快速构建
 */
public class SuperTextViewBean {

    private String leftTop;
    private String leftCenter;
    private String leftBottom;
    private String top;
    private String center;
    private String bottom;
    private String rightTop;
    private String rightCenter;
    private String rightBottom;

    public SuperTextViewBean(String leftCenter, String rightCenter) {
        this.leftCenter = leftCenter;
        this.rightCenter = rightCenter;
    }

    public String getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(String leftTop) {
        this.leftTop = leftTop;
    }

    public String getLeftCenter() {
        return leftCenter;
    }

    public void setLeftCenter(String leftCenter) {
        this.leftCenter = leftCenter;
    }

    public String getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(String leftBottom) {
        this.leftBottom = leftBottom;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getRightTop() {
        return rightTop;
    }

    public void setRightTop(String rightTop) {
        this.rightTop = rightTop;
    }

    public String getRightCenter() {
        return rightCenter;
    }

    public void setRightCenter(String rightCenter) {
        this.rightCenter = rightCenter;
    }

    public String getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(String rightBottom) {
        this.rightBottom = rightBottom;
    }
}
