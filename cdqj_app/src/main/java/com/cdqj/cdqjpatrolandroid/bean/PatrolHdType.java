package com.cdqj.cdqjpatrolandroid.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PatrolHdType extends RealmObject{
    /**
     * value : 34053053
     * text : 安全间距不足
     * attributes : null
     * selected : false
     */
    @PrimaryKey
    private Long value;
    private String text;
    private String attributes;
    private boolean selected;
    private String moduleCode;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
