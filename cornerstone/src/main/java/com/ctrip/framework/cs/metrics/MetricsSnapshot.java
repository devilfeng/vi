package com.ctrip.framework.cs.metrics;

/**
 * Created by jiang.j on 2016/8/19.
 */
public class MetricsSnapshot {
    public int count;
    public long total;
    public long min;
    public long max;
    public double stddev;
    public double[] percentileValues;
    protected MetricsSnapshot() {

    }
}
