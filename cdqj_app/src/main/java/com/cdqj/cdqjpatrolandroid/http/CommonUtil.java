package com.cdqj.cdqjpatrolandroid.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/6/22 17:43
 *
 * @author lyf
 * desc：添加header
 */
public class CommonUtil {

    /**
     * 接口传json map  里面存放header + body
     * String 类型  否则报错
     */
    public static Map getServiceParamsMap(Map<String, String> header, Object body) {
        Map<String, Object> map = new HashMap<>(2);
        //指定key 为header
        map.put("header",header);
        //指定key为body
        map.put("body",body);
        return map;
    }
}
