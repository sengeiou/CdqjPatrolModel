package com.cdqj.cdqjpatrolandroid.gis.common;

/**
 * gps转换用途类型
 */
public enum  GpsConvertToDoType {
    Locate,   //用于地图定位
    Nav,      //用于地图导航坐标转换
    InputXy,  //用户输入坐标定位
    GDNav,     //用于工地导航
    To2000ForAddr,     //转为2000
}