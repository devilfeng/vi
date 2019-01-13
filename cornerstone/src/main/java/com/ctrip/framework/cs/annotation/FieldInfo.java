package com.ctrip.framework.cs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiang.j on 2016/3/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldInfo {
    /**
     * the value indicating a version number since this member
     * or type has been present.
     */
    String name();

    String description() default "";

    FieldType type() default FieldType.Txt;

    public enum FieldType {
        Txt,
        Bytes,
        Number,
        Date
    }
}
