package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import static com.cdqj.cdqjpatrolandroid.adapter.PiesRvAdapter.TYPE_LEVEL_1;
import static com.cdqj.cdqjpatrolandroid.adapter.PiesRvAdapter.TYPE_PERSON;

public class PiesChildItem implements MultiItemEntity, Parcelable {
    String text;
    boolean isSelect = false;
    int index;
    int parentIndex;
    private String id;
    private String pid;
    private String textX;
    private String state;

    @Override
    public String toString() {
        return "PiesChildItem{" +
                "text='" + text + '\'' +
                ", isSelect=" + isSelect +
                ", index=" + index +
                ", parentIndex=" + parentIndex +
                ", id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", textX='" + textX + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid == null ? "" : pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTextX() {
        return textX == null ? "" : textX;
    }

    public void setTextX(String textX) {
        this.textX = textX;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @Override
    public int getItemType() {
        return TYPE_LEVEL_1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.index);
        dest.writeInt(this.parentIndex);
        dest.writeString(this.id);
        dest.writeString(this.pid);
        dest.writeString(this.textX);
        dest.writeString(this.state);
    }

    public PiesChildItem() {
    }

    protected PiesChildItem(Parcel in) {
        this.text = in.readString();
        this.isSelect = in.readByte() != 0;
        this.index = in.readInt();
        this.parentIndex = in.readInt();
        this.id = in.readString();
        this.pid = in.readString();
        this.textX = in.readString();
        this.state = in.readString();
    }

    public static final Parcelable.Creator<PiesChildItem> CREATOR = new Parcelable.Creator<PiesChildItem>() {
        @Override
        public PiesChildItem createFromParcel(Parcel source) {
            return new PiesChildItem(source);
        }

        @Override
        public PiesChildItem[] newArray(int size) {
            return new PiesChildItem[size];
        }
    };
}
