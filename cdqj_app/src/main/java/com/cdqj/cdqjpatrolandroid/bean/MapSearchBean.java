package com.cdqj.cdqjpatrolandroid.bean;

import com.cdqj.cdqjpatrolandroid.adapter.MapSearchListAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lyf on 2018/8/21 19:16
 *
 * @author lyf
 * desc：
 */
public class MapSearchBean implements MultiItemEntity {

    public static final int ROAD = 1;
    public static final int DEVICE = 2;
    public static final int PERSONNEL = 3;
    public static final int SITE = 4;
    public static final int HD = 5;
    public static final int OTHER = 6;

    private String id;

    private int size;

    private int maxSize;

    private String name;
    private String address;
    private String level;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 类型标识
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MapSearchBean(int size,int type) {
        this.size = size;
        this.type = type;
    }

    @Override
    public int getItemType() {
        return MapSearchListAdapter.TYPE_LEVEL_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
