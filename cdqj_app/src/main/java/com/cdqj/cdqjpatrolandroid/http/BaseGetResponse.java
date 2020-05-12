package com.cdqj.cdqjpatrolandroid.http;

import java.util.List;

/**
 * Created by lyf on 2018/9/7 14:31
 *
 * @author lyf
 * desc：获取数据后返回的基类（获取通用get方法：如获取列表、详情等）
 */
public class BaseGetResponse<T> {

    /**
     * first : 0
     * order : string
     * page : 0
     * pageSize : 0
     * rows : [{"addTime":"2018-09-10T02:48:58.454Z"}]
     * sort : string
     * total : 0
     */

    private int first;
    private String order;
    /**
     * 页数
     */
    private int page;
    /**
     * 每页数量
     */
    private int pageSize;
    private String sort;
    /**
     * 总计
     */
    private int total;
    private List<T> rows;

    private String code;

    private String msg;

    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}