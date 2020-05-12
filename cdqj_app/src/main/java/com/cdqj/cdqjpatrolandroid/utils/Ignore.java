package com.cdqj.cdqjpatrolandroid.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lyf on 2018/9/27 10:38
 *
 * @author lyf
 * desc：注解Ignore 序列化过滤
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {
}
