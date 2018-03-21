package com.wooki.function.annotation;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/22
 * Time : 下午6:16
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 运行时
@Target({ElementType.TYPE,ElementType.METHOD})       // 类
public @interface Admin {
}
