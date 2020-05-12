package com.cdqj.cdqjpatrolandroid.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransformUtils {
    public static <T> Observable.Transformer<T, T> defaultSchedulers() {
        return tObservable -> tObservable
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}