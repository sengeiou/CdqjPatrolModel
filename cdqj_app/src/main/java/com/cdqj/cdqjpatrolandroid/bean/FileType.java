package com.cdqj.cdqjpatrolandroid.bean;

public class FileType {
    /**
     * url  文件地址
     * type 1.图片2.音频3.视屏
     */
    String url;
    int type;

    public FileType(String url, int type) {
        this.url = url;
        this.type = type;
    }

    @Override
    public String toString() {
        return "FileType{" +
                "url='" + url + '\'' +
                ", type=" + type +
                '}';
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
