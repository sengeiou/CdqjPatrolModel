package com.cdqj.cdqjpatrolandroid.download.queue;

/**
 * Created by lyf on 2018/8/24 09:56
 *
 * @author lyf
 * desc：文件下载对象
 */
public class QueueDownLoadBean {

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件下载对应的key
     */
    private String key;

    /**
     * 文件下载保存地址
     */
    private String path;

    /**
     * 文件下载保存地址
     */
    private String name;

    public QueueDownLoadBean() {
    }

    public QueueDownLoadBean(String url, String key, String path, String name) {
        this.url = url;
        this.key = key;
        this.path = path;
        this.name = name;
    }

    public QueueDownLoadBean(String url, String key) {
        this.url = url;
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
