package com.ctrip.framework.cs.metrics;

/**
 * Created by jiang.j on 2016/12/30.
 */
public interface MetricDefine {
    String valueDescription();

    String valueTransFunc();

    Class<? extends Enum> tags();
}
