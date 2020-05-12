package com.cdqj.cdqjpatrolandroid.bean;

/**
 * Created by lyf on 2018/11/7 09:30
 *
 * @author lyf
 * desc：
 */
public class SearchRoundRequestBean {

    /**
     * keyWord : 万达广场
     * level : 18
     * mapBound : 113.29116,40.07126,113.29682,40.07181
     * queryType : 1
     * start : 0
     * count : 10
     */

    private String keyWord;
    private String level;
    private String mapBound;
    private String queryType;
    private String start;
    private String count;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMapBound() {
        return mapBound;
    }

    public void setMapBound(String mapBound) {
        this.mapBound = mapBound;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
