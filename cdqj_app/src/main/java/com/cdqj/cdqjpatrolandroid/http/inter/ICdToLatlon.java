package com.cdqj.cdqjpatrolandroid.http.inter;

import com.esri.arcgisruntime.geometry.Point;

import java.util.List;

public interface ICdToLatlon {
    void getPoints(List<Point> points);
}