package cn.walink.clairvoyant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用：
 * 创建者：陈佳润
 * 创建日期：16/5/11
 * 更新历史：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {

    String msg();

    int seq() default 0;

    int[] group() default {0};
}
