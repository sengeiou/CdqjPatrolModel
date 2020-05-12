package com.cdqj.cdqjpatrolandroid.utils;

import com.cdqj.cdqjpatrolandroid.bean.MapBean;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by lyf on 2018/1/16.
 * 数据转换类
 */
public class ConvertorDataUtils {


    public static Map<String,Object> json2Map(String str){
        Map<String,Object> map = GsonUtils.gsonBuilder.create().fromJson(str,new TypeToken<Map<String,Object>>() {}.getType());
        return map;
    }

    /**
     * MAP转换为一个集合对象
     * @param map map
     * @return List
     */
    public static List<MapBean> convertorMap2ListBean(Map<String, Object> map) {
        List<MapBean> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            MapBean bean = new MapBean();
            bean.setKey(entry.getKey());
            bean.setValue(entry.getValue());
            list.add(bean);
        }
        return list;
    }

    public static List convertorMap2List(Map<String, Object> map) {
        // 将Map Key 转化为List
        List<String> mapKeyList = new ArrayList<>(map.keySet());
        // 将Map Key 转化为List
        List mapValuesList = new ArrayList<>(map.values());
        List list = new ArrayList<>();
        list.add(mapKeyList);
        list.add(mapValuesList);
        return list;
    }

    public static List convertorMap2Set(Map<String, Object> map) {
        // 将Map 的键转化为Set
        Set<String> mapKeySet = map.keySet();
        // 将Map 的值转化为Set
        Set<Object> mapValuesSet = new HashSet<>(map.values());
        List list = new ArrayList<>();
        list.add(mapKeySet);
        list.add(mapValuesSet);
        return list;
    }

    /**
     * 数组-->Set
     * @param arr arr
     * @return set
     */
    public static Set<Object> convertorArray2Set(Object[] arr) {
        Set<Object> set = new HashSet<>(Arrays.asList(arr));
        return set;
    }

    public static Object[] convertorSet2Array() {
        Set<Object> set = new HashSet<>();
        set.add("AA");
        set.add("BB");
        set.add("CC");

        Object[] arr = new Object[set.size()];
        //Set-->数组
        set.toArray(arr);
        return arr;
    }

    /**
     * List-->Set
     * @param list list
     * @return Set
     */
    public static Set<Object> convertorList2Set(List list) {
        Set<Object> listSet = new HashSet<>(list);
        return listSet;
    }

    /**
     * Set --> List
     * @param set Set
     * @return List
     */
    public static List<String> convertorSet2List(Set<Object> set) {
        List setList = new ArrayList<>(set);
        return setList;
    }

    /**
     * List-->数组
     * @param list List
     * @return arr
     */
    public static Object[] convertorList2Array(List list) {
        Object[] arr = new Object[list.size()];
        //将转化后的数组放入已经创建好的对象中
        list.toArray(arr);
        return arr;
    }

    /**
     * 数组-->List
     * @param ss 数组
     * @return list
     */
    public static List convertorArray2List(Object[] ss) {
        return Arrays.asList(ss);
    }

}
