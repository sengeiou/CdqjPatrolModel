package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.cdqj.cdqjpatrolandroid.adapter.PiesRvAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.cdqj.cdqjpatrolandroid.adapter.MapSearchListAdapter.TYPE_LEVEL_0;
import static com.cdqj.cdqjpatrolandroid.adapter.PiesRvAdapter.TYPE_LEVEL_1;

public class PiesParentItem extends AbstractExpandableItem<PiesChildItem> implements MultiItemEntity, Parcelable {
    private String text;
    int index;
    private String id;
    private String pid;
    private String state;

    @Override
    public String toString() {
        return "PiesParentItem{" +
                "text='" + text + '\'' +
                ", index=" + index +
                ", id='" + id + '\'' +
                ", pid='" + pid + '\'' +
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

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TYPE_LEVEL_0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeInt(this.index);
        dest.writeString(this.id);
        dest.writeString(this.pid);
        dest.writeString(this.state);
    }

    public PiesParentItem() {
    }

    protected PiesParentItem(Parcel in) {
        this.text = in.readString();
        this.index = in.readInt();
        this.id = in.readString();
        this.pid = in.readString();
        this.state = in.readString();
    }

    public static final Parcelable.Creator<PiesParentItem> CREATOR = new Parcelable.Creator<PiesParentItem>() {
        @Override
        public PiesParentItem createFromParcel(Parcel source) {
            return new PiesParentItem(source);
        }

        @Override
        public PiesParentItem[] newArray(int size) {
            return new PiesParentItem[size];
        }
    };
}
