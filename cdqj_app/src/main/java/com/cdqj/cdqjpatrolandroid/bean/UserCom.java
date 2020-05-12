package com.cdqj.cdqjpatrolandroid.bean;


import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class UserCom extends RealmObject{
    String value,text;
    boolean selected;
    /**
     * id : 34051693
     * pid : null
     * state : open
     * checked : false
     * attributes : null
     * children : [{"id":"34053665","pid":"34051693","text":"冰冰","state":"open","checked":false,"attributes":null,"children":[]}]
     */
    @PrimaryKey
    private String id;
    private String pid;
    private String state;
    private boolean checked;
//    private String attributes;
    @Ignore
    private List<ChildrenBean> children;

    @Override
    public String toString() {
        return "UserCom{" +
                "value='" + value + '\'' +
                ", text='" + text + '\'' +
                ", selected=" + selected +
                ", id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", state='" + state + '\'' +
                ", checked=" + checked +
//                ", attributes='" + attributes + '\'' +
                ", children=" + children +
                '}';
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

//    public String getAttributes() {
//        return attributes;
//    }

//    public void setAttributes(String attributes) {
//        this.attributes = attributes;
//    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 34053665
         * pid : 34051693
         * text : 冰冰
         * state : open
         * checked : false
         * attributes : null
         * children : []
         */

        private String id;
        private String pid;
        private String text;
        private String state;
        private boolean checked;
        private String attributes;
        private List<?> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getText() {
            return text;
        }

        public void setText(String textX) {
            this.text = textX;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }
    }
}
