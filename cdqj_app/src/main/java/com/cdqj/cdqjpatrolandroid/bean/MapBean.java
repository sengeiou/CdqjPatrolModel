package com.cdqj.cdqjpatrolandroid.bean;

/**
 * Created by lyf on 2018/9/13 17:45
 *
 * @author lyf
 * desc：
 */
public class MapBean {
    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public MapBean(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public MapBean() {
    }

    @Override
    public String toString() {
        return "MapBean{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
