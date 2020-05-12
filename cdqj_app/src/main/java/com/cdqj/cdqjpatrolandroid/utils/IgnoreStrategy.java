package com.cdqj.cdqjpatrolandroid.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Created by lyf on 2018/9/27 10:35
 *
 * @author lyf
 * desc：生成ExclusionStrategy实现类 用于注释过滤json序列化及反序列化
 */
public class IgnoreStrategy implements ExclusionStrategy {

    /**
     * (non-Javadoc)
     *
     * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
     */
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        Collection<Annotation> annotations = fieldAttributes.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Ignore.class) {
                return true;
            }
        }
        return false;
    }
}
