package com.cdqj.cdqjpatrolandroid.bean;

/**
 * Created by lyf on 2018/10/11 09:55
 *
 * @author lyf
 * desc：计划内任务对象
 */
public class TaskBean {
    /**
     * 进度
     */
    private int progress;
    /**
     * 代码
     */
    private String code;
    /**
     * 名字
     */
    private String name;
    /**
     * 总数
     */
    private int count;
    /**
     * 当前
     */
    private int cpt;
    /**
     * 总数
     */
    private double countT;
    /**
     * 当前
     */
    private double cptT;

    public double getCountT() {
        return countT;
    }

    public void setCountT(double countT) {
        this.countT = countT;
    }

    public double getCptT() {
        return cptT;
    }

    public void setCptT(double cptT) {
        this.cptT = cptT;
    }

    public TaskBean(int progress, String name) {
        this.progress = progress;
        this.name = name;
    }

    public TaskBean(int progress, String code, String name) {
        this.progress = progress;
        this.code = code;
        this.name = name;
    }

    public TaskBean(int progress, int count, int cpt) {
        this.progress = progress;
        this.count = count;
        this.cpt = cpt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCpt() {
        return cpt;
    }

    public void setCpt(int cpt) {
        this.cpt = cpt;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
